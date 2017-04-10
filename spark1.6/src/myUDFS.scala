package org.apache.spark.examples

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{ StringType, DoubleType, IntegerType, FloatType }
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.StructField

object myUDFS {

  val delim = ","
  val schema = StructType(Seq("number:Int", "value:String", "alpha:String").map(
    x =>
      {
        val x1 = x.split(":")
        new StructField(x1(0), x1(1) match {
          case "Int"    => IntegerType
          case "String" => StringType
          case "double" => DoubleType
          case _        => StringType
        })
      }))

  implicit class dqCheck(val self: RDD[String]) extends AnyVal {
    def createRowWithSchemaDQ(): RDD[(Row, String)] = {
      val myArray = new Array[Any](schema.size)
      self.map { x =>
        val in = x.split(delim, schema.size)
        var message = "valid"
        if (in.size != myArray.length) {
          message = "schema and input column count doesn't match"
        } else {
          for (i <- schema.zipWithIndex) {
            try {
              myArray(i._2) = i._1.dataType match {
                case StringType  => in(i._2).toString
                case DoubleType  => in(i._2).toDouble
                case IntegerType => in(i._2).toInt
                case FloatType   => in(i._2).toFloat
                case _           => throw new Exception(s"data type $i._1.dataType is not supported yet ")
              }
            } catch {
              case e: Exception => {
                message = "Error while parsing input, cast exception " + e.getMessage
              }
            }
          }
        }
        (if (message.equals("valid")) Row.fromSeq(myArray) else Row.fromSeq(in), message)
      }
    }
  }
}