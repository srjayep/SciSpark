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
package org.dia.tensors.perf

import org.nd4j.linalg.factory.Nd4j
import org.scalatest.FunSuite

/**
 * Nd4j Performance Tests
 */
class Nd4JPerformanceTest extends FunSuite {

  ignore("ND4J.element.wise.test") {
    println("ND4J.element.wise.test")
    (1 to 20).foreach { i =>
      val m1 = Nd4j.create(i * 1000 * i * 1000).reshape(i * 1000, i * 1000)
      val m2 = Nd4j.create(i * 1000 * i * 1000).reshape(i * 1000, i * 1000)
      /**
       * subtraction
       */
      val start = System.nanoTime()
      val m3 = m1.sub(m2)
      val stop = System.nanoTime()
      println(stop - start)
    }
    assert(true)
  }

  ignore("ND4J.vector.wise.test") {
    println("ND4J.vector.wise.test")
    (1 to 20).foreach { i =>
      val m1 = Nd4j.create(i * 1000 * i * 1000).reshape(i * 1000, i * 1000)
      val m2 = Nd4j.create(i * 1000 * i * 1000).reshape(i * 1000, i * 1000)
      /**
       * element-wise multiplication
       */
      val start = System.nanoTime()
      val m3 = m1.mul(m2)
      val stop = System.nanoTime()
      println(stop - start)
    }
    assert(true)
  }

}
