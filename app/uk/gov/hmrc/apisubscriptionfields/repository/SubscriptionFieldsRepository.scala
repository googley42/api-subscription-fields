/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.apisubscriptionfields.repository

import javax.inject.{Inject, Singleton}

import com.google.inject.ImplementedBy
import play.api.Logger
import play.api.libs.json._
import reactivemongo.api.indexes.IndexType
import reactivemongo.bson.BSONObjectID
import reactivemongo.play.json.collection.JSONCollection
import uk.gov.hmrc.apisubscriptionfields.model._
import uk.gov.hmrc.mongo.ReactiveRepository
import uk.gov.hmrc.mongo.json.ReactiveMongoFormats

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


@ImplementedBy(classOf[SubscriptionFieldsMongoRepository])
trait SubscriptionFieldsRepository {

  def saveAtomic(subscription: SubscriptionFields): Future[(SubscriptionFields, Boolean)]

  def fetch(clientId: ClientId, apiContext: ApiContext, apiVersion: ApiVersion): Future[Option[SubscriptionFields]]
  def fetchByFieldsId(fieldsId: SubscriptionFieldsId): Future[Option[SubscriptionFields]]
  def fetchByClientId(clientId: ClientId): Future[List[SubscriptionFields]]
  def fetchAll(): Future[List[SubscriptionFields]]

  def delete(clientId: ClientId, apiContext: ApiContext, apiVersion: ApiVersion): Future[Boolean]
}

@Singleton
class SubscriptionFieldsMongoRepository @Inject()(mongoDbProvider: MongoDbProvider)
  extends ReactiveRepository[SubscriptionFields, BSONObjectID]("subscriptionFields", mongoDbProvider.mongo,
    MongoFormatters.SubscriptionFieldsJF, ReactiveMongoFormats.objectIdFormats)
  with SubscriptionFieldsRepository
  with MongoCrudHelper[SubscriptionFields] {

  override val mongoCollection: JSONCollection = collection

  private implicit val format = MongoFormatters.SubscriptionFieldsJF

  override def indexes = Seq(
    createCompoundIndex(
      indexFieldMappings = Seq(
        "clientId"   -> IndexType.Ascending,
        "apiContext" -> IndexType.Ascending,
        "apiVersion" -> IndexType.Ascending
      ),
      indexName = Some("clientId-apiContext-apiVersion_Index"),
      isUnique = true
    ),
    createSingleFieldAscendingIndex(
      indexFieldKey = "clientId",
      indexName = Some("clientIdIndex"),
      isUnique = false
    ),
    createSingleFieldAscendingIndex(
      indexFieldKey = "fieldsId",
      indexName = Some("fieldsIdIndex"),
      isUnique = true
    )
  )

  override def saveAtomic(subscription: SubscriptionFields): Future[(SubscriptionFields, Boolean)] = {
    import reactivemongo.play.json._

    val selector = selectorForSubscriptionFields(subscription)
    val entity = subscription
    Logger.debug(s"[save] entity: $entity selector: $selector")

    // note that $setOnInsert operation will only happen on insert, it is ignored for updates.
    val updateOp = mongoCollection.updateModifier(
      Json.obj("$setOnInsert" -> Json.obj("fieldsId" -> subscription.fieldsId.toString), "$set" -> Json.obj("fields" -> Json.toJson(subscription.fields))),
      fetchNewObject = true,
      upsert = true
    )

    mongoCollection.findAndModify(selector, updateOp).map {findAndModifyResult =>
      val maybeTuple: Option[(SubscriptionFields, Boolean)] = for {
        value <- findAndModifyResult.value
        updateLastError <- findAndModifyResult.lastError
      } yield (value.as[SubscriptionFields], !updateLastError.updatedExisting)

      maybeTuple.fold[(SubscriptionFields, Boolean)]{
        val error = s"Error upserting database for $selector. lastError: ${findAndModifyResult.lastError}"
        Logger.error(error)
        throw new RuntimeException(error)
      }(tuple => tuple)
    }
  }

  override def fetch(clientId: ClientId, apiContext: ApiContext, apiVersion: ApiVersion): Future[Option[SubscriptionFields]] = {
    getOne(selectorForSubscriptionFields(clientId.value, apiContext.value, apiVersion.value))
  }

  override def fetchByFieldsId(fieldsId: SubscriptionFieldsId): Future[Option[SubscriptionFields]] = {
    getOne(Json.obj("fieldsId" -> fieldsId.value))
  }

  override def fetchByClientId(clientId: ClientId): Future[List[SubscriptionFields]] = {
    getMany(Json.obj("clientId" -> clientId.value))
  }

  override def fetchAll(): Future[List[SubscriptionFields]] = {
    getMany(Json.obj())
  }

  override def delete(clientId: ClientId, apiContext: ApiContext, apiVersion: ApiVersion): Future[Boolean] = {
    deleteOne(selectorForSubscriptionFields(clientId.value, apiContext.value, apiVersion.value))
  }

  private def selectorForSubscriptionFields(clientId: String, apiContext: String, apiVersion: String): JsObject = {
    Json.obj(
      "clientId"   -> clientId,
      "apiContext" -> apiContext,
      "apiVersion" -> apiVersion
    )
  }

  private def selectorForSubscriptionFields(subscription: SubscriptionFields): JsObject = {
    selectorForSubscriptionFields(subscription.clientId, subscription.apiContext, subscription.apiVersion)
  }

}
