package org.dia.core

import java.io.Serializable
import org.dia.algorithms.mcc.mccOps
import org.dia.tensors.AbstractTensor
import org.slf4j.Logger
import scala.collection.mutable
import scala.language.implicitConversions

/**
 * The SciTensor is a self-documented array object. It stores N number of variable arrays.
 * The SciTensor also keeps track of a metadata table for properties which the user may want to record.
 * Note that all linear algebra and ocw operations on SciTensors are performed on the variable in use.
 * Furthermore SciTensors are treated as immutable objects and so all operations return new SciTensor objects.
 *
 * @param variables A hashmap of variable name to the tensor of variable values.
 */
class SciTensor(val variables: mutable.HashMap[String, AbstractTensor]) extends Serializable {

  val LOG = org.slf4j.LoggerFactory.getLogger(this.getClass)
  val metaData = new mutable.HashMap[String, String]
  var varInUse = variables.toArray.apply(0)._1

  def this(variableName: String, array: AbstractTensor) {
    this(new mutable.HashMap[String, AbstractTensor] += ((variableName, array)))
  }

  def this(variableName: String, array: AbstractTensor, metaDataVar: (String, String)*) {
    this(variableName, array)
    metaDataVar.map(p => metaData += p)
  }

  /**
   * Writes metaData in the form of key-value pairs
   */
  def insertDictionary(metaDataVar: (String, String)*): Unit = {
    for (variable <- metaDataVar) metaData += variable
  }

  /**
   * Slices the head variable array given the list of ranges per dimension.
   */
  def apply(ranges: (Int, Int)*): SciTensor = {
    variables(varInUse)(ranges: _*)
  }

  /**
   * Shifts the variable in use pointer to a different variable array.
   * If the variable is not found, error message to log4j.
   */
  def apply(variable: String): SciTensor = {
    if (variables.keySet.contains(variable)) {
      varInUse = variable
    } else {
      LOG.error("Variable " + variable + " was NOT FOUND in the variable array table.")
    }
    this
  }

  def +(other: SciTensor): SciTensor = this.tensor + other.tensor

  /**
   * Returns the variable array that is currently being used
   */
  def tensor: AbstractTensor = variables(varInUse)

  def -(other: SciTensor): SciTensor = this.tensor - other.tensor

  def \(other: SciTensor): SciTensor = this.tensor \ other.tensor

  def /(other: SciTensor): SciTensor = this.tensor / other.tensor

  def *(other: SciTensor): SciTensor = this.tensor * other.tensor

  /**
   * Linear Algebra Operations
   */
  def **(other: SciTensor): SciTensor = this.tensor ** other.tensor

  /**
   * Masks the current variable array by preserving values
   * less than or equal to num.
   */
  def <=(num: Double): SciTensor = variables(varInUse) <= num

  /**
   * Returns a block averaged tensor where the blocks are squares with
   * dimensions blockInt.
   */
  def reduceResolution(blockInt: Int, invalid: Double = Double.NaN): SciTensor = {
    mccOps.reduceResolution(variables(varInUse), blockInt, invalid)
  }

  /**
   * ------------------------------ Matrix Operations ---------------------------------
   * The following functions are Matrix Operations specific to SciSpark and it's goals.
   */

  /**
   * Returns a block averaged matrix where the blocks are rectangles with dimensions
   * rowblockSize X colblockSize.
   */
  def reduceRectangleResolution(rowblockSize: Int, colblockSize: Int, invalid: Int): SciTensor = {
    mccOps.reduceRectangleResolution(variables(varInUse), rowblockSize, colblockSize, invalid)
  }

  override def toString: String = {
    var string = "Variable in use = " + varInUse + "\n" + variables.keys.toString + "\n"
    metaData.foreach(string += _ + "\n")
    string
  }

  /**
   * An implicit converter that is called on every SciTensor operator function.
   */
  private implicit def convert(tensor: AbstractTensor): SciTensor = new SciTensor(varInUse, tensor, metaData)

  def this(variableName: String, array: AbstractTensor, metaDataVar: mutable.HashMap[String, String]) {
    this(variableName, array)
    metaDataVar.map(p => metaData += p)
  }
  
}
