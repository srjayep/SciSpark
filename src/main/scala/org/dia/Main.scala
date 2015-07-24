/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dia

import breeze.linalg.DenseMatrix
import org.dia.Constants._
import org.dia.tensors.BreezeTensor
import org.dia.core.{sciTensor, SciSparkContext}

import scala.language.implicitConversions

/**
 */
object Main {

  /**
   * NetCDF variables to use
   * TODO:: Make the netcdf variables global - however this may need broadcasting
   */
  val rowDim = 180
  val columnDim = 360
  val TextFile = "TestLinks"


  def main(args: Array[String]): Unit = {
    val sc = new SciSparkContext("local[4]", "test")

    sc.setLocalProperty(ARRAY_LIB, ND4J_LIB)

    val variable = "TotCldLiqH2O_A"

    val nd4jRDD = sc.OpenDapURLFile("TestLinks2", variable)

    val preCollected  = nd4jRDD.map(p => p(variable).reduceResolution(5))

    val filtered = preCollected.map(p => p(variable) <= 241.0)

    val Sliced = filtered.map(p => p(variable)(4 -> 9, 2 -> 5))

    val collected: Array[sciTensor] = Sliced.collect

    println(collected.toList)
  }
}


