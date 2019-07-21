package com.latitude55.bookkeeper.service
import scala.io.Source

class BookkeeperService {
  def getPredictions: Array[(Int,Int)] = {
    var results = scala.collection.mutable.ArrayBuffer.empty[(Int,Int)]
    val bufferedSource = scala.io.Source.fromFile("/Downloads/dataset2.csv")
    for (line <- bufferedSource.getLines) {
      val cols = line.split(",").map(_.trim)
      // do whatever you want with the columns here
      results.append((cols(1).toInt,cols(2).toInt))
    }
    bufferedSource.close
    results.toArray
  }
}
