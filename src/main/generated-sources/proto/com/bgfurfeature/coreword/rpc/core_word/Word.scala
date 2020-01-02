// Generated by the Scala Plugin for the Protocol Buffer Compiler.
// Do not edit!
//
// Protofile syntax: PROTO3

package com.bgfurfeature.coreword.rpc.core_word



@SerialVersionUID(0L)
final case class Word(
    number: String = "",
    text: String = ""
    ) extends com.trueaccord.scalapb.GeneratedMessage with com.trueaccord.scalapb.Message[Word] with com.trueaccord.lenses.Updatable[Word] {
    @transient
    private[this] var __serializedSizeCachedValue: Int = 0
    private[this] def __computeSerializedValue(): Int = {
      var __size = 0
      if (number != "") { __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(1, number) }
      if (text != "") { __size += _root_.com.google.protobuf.CodedOutputStream.computeStringSize(2, text) }
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
        val __v = number
        if (__v != "") {
          _output__.writeString(1, __v)
        }
      };
      {
        val __v = text
        if (__v != "") {
          _output__.writeString(2, __v)
        }
      };
    }
    def mergeFrom(`_input__`: _root_.com.google.protobuf.CodedInputStream): com.bgfurfeature.coreword.rpc.core_word.Word = {
      var __number = this.number
      var __text = this.text
      var _done__ = false
      while (!_done__) {
        val _tag__ = _input__.readTag()
        _tag__ match {
          case 0 => _done__ = true
          case 10 =>
            __number = _input__.readString()
          case 18 =>
            __text = _input__.readString()
          case tag => _input__.skipField(tag)
        }
      }
      com.bgfurfeature.coreword.rpc.core_word.Word(
          number = __number,
          text = __text
      )
    }
    def withNumber(__v: String): Word = copy(number = __v)
    def withText(__v: String): Word = copy(text = __v)
    def getFieldByNumber(__fieldNumber: Int): scala.Any = {
      (__fieldNumber: @_root_.scala.unchecked) match {
        case 1 => {
          val __t = number
          if (__t != "") __t else null
        }
        case 2 => {
          val __t = text
          if (__t != "") __t else null
        }
      }
    }
    def getField(__field: _root_.scalapb.descriptors.FieldDescriptor): _root_.scalapb.descriptors.PValue = {
      require(__field.containingMessage eq companion.scalaDescriptor)
      (__field.number: @_root_.scala.unchecked) match {
        case 1 => _root_.scalapb.descriptors.PString(number)
        case 2 => _root_.scalapb.descriptors.PString(text)
      }
    }
    override def toString: String = _root_.com.trueaccord.scalapb.TextFormat.printToUnicodeString(this)
    def companion = com.bgfurfeature.coreword.rpc.core_word.Word
}

object Word extends com.trueaccord.scalapb.GeneratedMessageCompanion[com.bgfurfeature.coreword.rpc.core_word.Word] {
  implicit def messageCompanion: com.trueaccord.scalapb.GeneratedMessageCompanion[com.bgfurfeature.coreword.rpc.core_word.Word] = this
  def fromFieldsMap(__fieldsMap: scala.collection.immutable.Map[_root_.com.google.protobuf.Descriptors.FieldDescriptor, scala.Any]): com.bgfurfeature.coreword.rpc.core_word.Word = {
    require(__fieldsMap.keys.forall(_.getContainingType() == javaDescriptor), "FieldDescriptor does not match message type.")
    val __fields = javaDescriptor.getFields
    com.bgfurfeature.coreword.rpc.core_word.Word(
      __fieldsMap.getOrElse(__fields.get(0), "").asInstanceOf[String],
      __fieldsMap.getOrElse(__fields.get(1), "").asInstanceOf[String]
    )
  }
  implicit def messageReads: _root_.scalapb.descriptors.Reads[com.bgfurfeature.coreword.rpc.core_word.Word] = _root_.scalapb.descriptors.Reads{
    case _root_.scalapb.descriptors.PMessage(__fieldsMap) =>
      require(__fieldsMap.keys.forall(_.containingMessage == scalaDescriptor), "FieldDescriptor does not match message type.")
      com.bgfurfeature.coreword.rpc.core_word.Word(
        __fieldsMap.get(scalaDescriptor.findFieldByNumber(1).get).map(_.as[String]).getOrElse(""),
        __fieldsMap.get(scalaDescriptor.findFieldByNumber(2).get).map(_.as[String]).getOrElse("")
      )
    case _ => throw new RuntimeException("Expected PMessage")
  }
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.Descriptor = CoreWordProto.javaDescriptor.getMessageTypes.get(0)
  def scalaDescriptor: _root_.scalapb.descriptors.Descriptor = CoreWordProto.scalaDescriptor.messages(0)
  def messageCompanionForFieldNumber(__fieldNumber: Int): _root_.com.trueaccord.scalapb.GeneratedMessageCompanion[_] = throw new MatchError(__fieldNumber)
  def enumCompanionForFieldNumber(__fieldNumber: Int): _root_.com.trueaccord.scalapb.GeneratedEnumCompanion[_] = throw new MatchError(__fieldNumber)
  lazy val defaultInstance = com.bgfurfeature.coreword.rpc.core_word.Word(
  )
  implicit class WordLens[UpperPB](_l: _root_.com.trueaccord.lenses.Lens[UpperPB, com.bgfurfeature.coreword.rpc.core_word.Word]) extends _root_.com.trueaccord.lenses.ObjectLens[UpperPB, com.bgfurfeature.coreword.rpc.core_word.Word](_l) {
    def number: _root_.com.trueaccord.lenses.Lens[UpperPB, String] = field(_.number)((c_, f_) => c_.copy(number = f_))
    def text: _root_.com.trueaccord.lenses.Lens[UpperPB, String] = field(_.text)((c_, f_) => c_.copy(text = f_))
  }
  final val NUMBER_FIELD_NUMBER = 1
  final val TEXT_FIELD_NUMBER = 2
}