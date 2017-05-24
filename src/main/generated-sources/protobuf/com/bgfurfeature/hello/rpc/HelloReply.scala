// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package com.bgfurfeature.hello.rpc

import com.bgfurfeature.hello.rpc

/** The response message containing the greetings
  */
@SerialVersionUID(0L)
final case class HelloReply(
    message: String = ""
    ) extends com.trueaccord.scalapb.GeneratedMessage with com.trueaccord.scalapb.Message[HelloReply] with com.trueaccord.lenses.Updatable[HelloReply] {
    @transient
    private[this] var __serializedSizeCachedValue: Int = 0
    private[this] def __computeSerializedValue(): Int = {
      var __size = 0
      if (message != "") { __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(1, message) }
      __size
    }
    final override def serializedSize: Int = {
      var read = __serializedSizeCachedValue
      if (read == 0) {
        read = __computeSerializedValue()
        __serializedSizeCachedValue = read
      }
      read
    }
    def writeTo(`_output__`: _root_.com.google.protobuf.CodedOutputStream): Unit = {
      {
        val __v = message
        if (__v != "") {
          _output__.writeString(1, __v)
        }
      };
    }
    def mergeFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): HelloReply = {
      var __message = this.message
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 10 =>
            __message = _input__.readString()
          case tag => _input__.skipField(tag)
        }
      }
      rpc.HelloReply(
          message = __message
      )
    }
    def withMessage(__v: String): HelloReply = copy(message = __v)
    def getFieldByNumber(__fieldNumber: Int): scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => {
          val __t = message
          if (__t != "") __t else null
        }
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PString(message)
      }
    }
    override def toString: String = _root_.com.trueaccord.scalapb.TextFormat.printToUnicodeString(this)
    def companion = rpc.HelloReply
}

object HelloReply extends com.trueaccord.scalapb.GeneratedMessageCompanion[HelloReply] {
  implicit def messageCompanion: com.trueaccord.scalapb.GeneratedMessageCompanion[HelloReply] = this
  def fromFieldsMap(__fieldsMap: scala.collection.immutable.Map[_root_.com.google.protobuf.Descriptors.FieldDescriptor, scala.Any]): HelloReply = {
    require(__fieldsMap.keys.forall(_.getContainingType() == javaDescriptor), "FieldDescriptor does not match message type.")
    val __fields = javaDescriptor.getFields
    rpc.HelloReply(
      __fieldsMap.getOrElse(__fields.get(0), "").asInstanceOf[String]
    )
  }
  implicit def messageReads: _root_.scalapb.descriptors.Reads[HelloReply] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      rpc.HelloReply(
        __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).map(_.as[String]).getOrElse("")
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = HelloProto.javaDescriptor.getMessageTypes.get(1)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = HelloProto.scalaDescriptor.messages(1)
  def messageCompanionForFieldNumber(__fieldNumber: Int): _root_.com.trueaccord.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__fieldNumber)
  def enumCompanionForFieldNumber(__fieldNumber: Int): _root_.com.trueaccord.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = rpc.HelloReply(
  )
  implicit class HelloReplyLens[UpperPB](_l: _root_.com.trueaccord.lenses.Lens[UpperPB, HelloReply]) extends _root_.com.trueaccord.lenses.ObjectLens[UpperPB, HelloReply](_l) {
    def message: _root_.com.trueaccord.lenses.Lens[UpperPB, String] = field(_.message)((c_, f_) => c_.copy(message = f_))
  }
  final val MESSAGE_FIELD_NUMBER = 1
}
