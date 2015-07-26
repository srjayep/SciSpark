package org.dia.tensors

import org.slf4j.Logger

/**
 */
 trait AbstractTensor  extends Serializable {
  type T <: AbstractTensor

  val name : String
  val LOG : Logger = org.slf4j.LoggerFactory.getLogger(this.getClass)

  /**
   * Reduces the resolution of a DenseMatrix
   * @param blockSize the size of n x n size of blocks.
   * @return
   */
  def reduceResolution (blockSize: Int): T

  /**
   *
   * @return c-ordered linear array of data elements
   */
 def data : Array[Double]

  /**
   * Elementwise Operations
   */

  def + (array : T) : T
  def - (array : T) : T

  def *(array : T) : T

  def /(array : T) : T

  def \ (array : T) : T

  /**
   * Linear Algebra Operations
   */

  def **(array : T) : T


  /**
   * Masking operations
   */

  def <=(num : Double) : T
  /**
   * In place operations
   */

//  def +=(array : T) : T
//
//  def -=(array : T) : T
//
//  def *=(array : T) : T
//
//  def **=(array : T) : T
//
//  def /=(array : T) : T
//
//  def \=(array : T) : T

  def toString : String

  def equals(array : T) : Boolean

  def getUnderlying() : (Array[Double], Array[Int]) = (data, shape)

//  def apply(ranges : (Int, Int)*) : T

  def shape : Array[Int]

 def apply(ranges : (Int, Int)*) : T
}
