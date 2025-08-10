package exercises

import scala.concurrent.{ExecutionContext, Future}
import zio.{ZIO, ZIOAppDefault}

object Ch01Exercise16 extends ZIOAppDefault {
  // 16. Using ZIO.fromFuture, convert the following code to ZIO:

  trait Query
  trait Result

  final case class ExampleQuery(query: String) extends Query
  final case class ExampleResult(result: String) extends Result

  def doQuery(query: Query)(implicit
    ec: ExecutionContext
  ): Future[Result] =
    query match {
      case ExampleQuery("valid-query") => Future.successful(ExampleResult("result"))
      case ExampleQuery(_) => Future.failed(new Throwable("query-failed"))
    }

  def doQueryZio(query: Query): ZIO[Any, Throwable, Result] =
    ZIO.fromFuture(ec => doQuery(query)(ec))

  val run = {
    for {
      result <- doQueryZio(ExampleQuery("valid-query"))
//      Uncomment the next line to test with an invalid key
//      result <- doQueryZio(ExampleQuery("invalid-query"))
    } yield println(s"result: $result")
  }
}

