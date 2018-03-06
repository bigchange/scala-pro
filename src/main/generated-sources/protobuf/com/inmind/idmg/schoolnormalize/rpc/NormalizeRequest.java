// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: school_normalize.proto

package com.inmind.idmg.schoolnormalize.rpc;

/**
 * Protobuf type {@code com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest}
 */
public  final class NormalizeRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest)
    NormalizeRequestOrBuilder {
  // Use NormalizeRequest.newBuilder() to construct.
  private NormalizeRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private NormalizeRequest() {
    features_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private NormalizeRequest(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
              features_ = new java.util.ArrayList<com.inmind.idmg.schoolnormalize.rpc.NormalFeature>();
              mutable_bitField0_ |= 0x00000001;
            }
            features_.add(
                input.readMessage(com.inmind.idmg.schoolnormalize.rpc.NormalFeature.parser(), extensionRegistry));
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
        features_ = java.util.Collections.unmodifiableList(features_);
      }
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.inmind.idmg.schoolnormalize.rpc.SchoolNormalize.internal_static_com_inmind_idmg_schoolnormalize_rpc_NormalizeRequest_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.inmind.idmg.schoolnormalize.rpc.SchoolNormalize.internal_static_com_inmind_idmg_schoolnormalize_rpc_NormalizeRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest.class, com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest.Builder.class);
  }

  public static final int FEATURES_FIELD_NUMBER = 1;
  private java.util.List<com.inmind.idmg.schoolnormalize.rpc.NormalFeature> features_;
  /**
   * <code>repeated .com.inmind.idmg.schoolnormalize.rpc.NormalFeature features = 1;</code>
   */
  public java.util.List<com.inmind.idmg.schoolnormalize.rpc.NormalFeature> getFeaturesList() {
    return features_;
  }
  /**
   * <code>repeated .com.inmind.idmg.schoolnormalize.rpc.NormalFeature features = 1;</code>
   */
  public java.util.List<? extends com.inmind.idmg.schoolnormalize.rpc.NormalFeatureOrBuilder> 
      getFeaturesOrBuilderList() {
    return features_;
  }
  /**
   * <code>repeated .com.inmind.idmg.schoolnormalize.rpc.NormalFeature features = 1;</code>
   */
  public int getFeaturesCount() {
    return features_.size();
  }
  /**
   * <code>repeated .com.inmind.idmg.schoolnormalize.rpc.NormalFeature features = 1;</code>
   */
  public com.inmind.idmg.schoolnormalize.rpc.NormalFeature getFeatures(int index) {
    return features_.get(index);
  }
  /**
   * <code>repeated .com.inmind.idmg.schoolnormalize.rpc.NormalFeature features = 1;</code>
   */
  public com.inmind.idmg.schoolnormalize.rpc.NormalFeatureOrBuilder getFeaturesOrBuilder(
      int index) {
    return features_.get(index);
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    for (int i = 0; i < features_.size(); i++) {
      output.writeMessage(1, features_.get(i));
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < features_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, features_.get(i));
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest)) {
      return super.equals(obj);
    }
    com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest other = (com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest) obj;

    boolean result = true;
    result = result && getFeaturesList()
        .equals(other.getFeaturesList());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptorForType().hashCode();
    if (getFeaturesCount() > 0) {
      hash = (37 * hash) + FEATURES_FIELD_NUMBER;
      hash = (53 * hash) + getFeaturesList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest)
      com.inmind.idmg.schoolnormalize.rpc.NormalizeRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.inmind.idmg.schoolnormalize.rpc.SchoolNormalize.internal_static_com_inmind_idmg_schoolnormalize_rpc_NormalizeRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.inmind.idmg.schoolnormalize.rpc.SchoolNormalize.internal_static_com_inmind_idmg_schoolnormalize_rpc_NormalizeRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest.class, com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest.Builder.class);
    }

    // Construct using com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
        getFeaturesFieldBuilder();
      }
    }
    public Builder clear() {
      super.clear();
      if (featuresBuilder_ == null) {
        features_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        featuresBuilder_.clear();
      }
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.inmind.idmg.schoolnormalize.rpc.SchoolNormalize.internal_static_com_inmind_idmg_schoolnormalize_rpc_NormalizeRequest_descriptor;
    }

    public com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest getDefaultInstanceForType() {
      return com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest.getDefaultInstance();
    }

    public com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest build() {
      com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest buildPartial() {
      com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest result = new com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest(this);
      int from_bitField0_ = bitField0_;
      if (featuresBuilder_ == null) {
        if (((bitField0_ & 0x00000001) == 0x00000001)) {
          features_ = java.util.Collections.unmodifiableList(features_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.features_ = features_;
      } else {
        result.features_ = featuresBuilder_.build();
      }
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest) {
        return mergeFrom((com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest other) {
      if (other == com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest.getDefaultInstance()) return this;
      if (featuresBuilder_ == null) {
        if (!other.features_.isEmpty()) {
          if (features_.isEmpty()) {
            features_ = other.features_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureFeaturesIsMutable();
            features_.addAll(other.features_);
          }
          onChanged();
        }
      } else {
        if (!other.features_.isEmpty()) {
          if (featuresBuilder_.isEmpty()) {
            featuresBuilder_.dispose();
            featuresBuilder_ = null;
            features_ = other.features_;
            bitField0_ = (bitField0_ & ~0x00000001);
            featuresBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getFeaturesFieldBuilder() : null;
          } else {
            featuresBuilder_.addAllMessages(other.features_);
          }
        }
      }
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<com.inmind.idmg.schoolnormalize.rpc.NormalFeature> features_ =
      java.util.Collections.emptyList();
    private void ensureFeaturesIsMutable() {
      if (!((bitField0_ & 0x00000001) == 0x00000001)) {
        features_ = new java.util.ArrayList<com.inmind.idmg.schoolnormalize.rpc.NormalFeature>(features_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.inmind.idmg.schoolnormalize.rpc.NormalFeature, com.inmind.idmg.schoolnormalize.rpc.NormalFeature.Builder, com.inmind.idmg.schoolnormalize.rpc.NormalFeatureOrBuilder> featuresBuilder_;

    /**
     * <code>repeated .com.inmind.idmg.schoolnormalize.rpc.NormalFeature features = 1;</code>
     */
    public java.util.List<com.inmind.idmg.schoolnormalize.rpc.NormalFeature> getFeaturesList() {
      if (featuresBuilder_ == null) {
        return java.util.Collections.unmodifiableList(features_);
      } else {
        return featuresBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .com.inmind.idmg.schoolnormalize.rpc.NormalFeature features = 1;</code>
     */
    public int getFeaturesCount() {
      if (featuresBuilder_ == null) {
        return features_.size();
      } else {
        return featuresBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .com.inmind.idmg.schoolnormalize.rpc.NormalFeature features = 1;</code>
     */
    public com.inmind.idmg.schoolnormalize.rpc.NormalFeature getFeatures(int index) {
      if (featuresBuilder_ == null) {
        return features_.get(index);
      } else {
        return featuresBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .com.inmind.idmg.schoolnormalize.rpc.NormalFeature features = 1;</code>
     */
    public Builder setFeatures(
        int index, com.inmind.idmg.schoolnormalize.rpc.NormalFeature value) {
      if (featuresBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureFeaturesIsMutable();
        features_.set(index, value);
        onChanged();
      } else {
        featuresBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .com.inmind.idmg.schoolnormalize.rpc.NormalFeature features = 1;</code>
     */
    public Builder setFeatures(
        int index, com.inmind.idmg.schoolnormalize.rpc.NormalFeature.Builder builderForValue) {
      if (featuresBuilder_ == null) {
        ensureFeaturesIsMutable();
        features_.set(index, builderForValue.build());
        onChanged();
      } else {
        featuresBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .com.inmind.idmg.schoolnormalize.rpc.NormalFeature features = 1;</code>
     */
    public Builder addFeatures(com.inmind.idmg.schoolnormalize.rpc.NormalFeature value) {
      if (featuresBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureFeaturesIsMutable();
        features_.add(value);
        onChanged();
      } else {
        featuresBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .com.inmind.idmg.schoolnormalize.rpc.NormalFeature features = 1;</code>
     */
    public Builder addFeatures(
        int index, com.inmind.idmg.schoolnormalize.rpc.NormalFeature value) {
      if (featuresBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureFeaturesIsMutable();
        features_.add(index, value);
        onChanged();
      } else {
        featuresBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .com.inmind.idmg.schoolnormalize.rpc.NormalFeature features = 1;</code>
     */
    public Builder addFeatures(
        com.inmind.idmg.schoolnormalize.rpc.NormalFeature.Builder builderForValue) {
      if (featuresBuilder_ == null) {
        ensureFeaturesIsMutable();
        features_.add(builderForValue.build());
        onChanged();
      } else {
        featuresBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .com.inmind.idmg.schoolnormalize.rpc.NormalFeature features = 1;</code>
     */
    public Builder addFeatures(
        int index, com.inmind.idmg.schoolnormalize.rpc.NormalFeature.Builder builderForValue) {
      if (featuresBuilder_ == null) {
        ensureFeaturesIsMutable();
        features_.add(index, builderForValue.build());
        onChanged();
      } else {
        featuresBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .com.inmind.idmg.schoolnormalize.rpc.NormalFeature features = 1;</code>
     */
    public Builder addAllFeatures(
        java.lang.Iterable<? extends com.inmind.idmg.schoolnormalize.rpc.NormalFeature> values) {
      if (featuresBuilder_ == null) {
        ensureFeaturesIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, features_);
        onChanged();
      } else {
        featuresBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .com.inmind.idmg.schoolnormalize.rpc.NormalFeature features = 1;</code>
     */
    public Builder clearFeatures() {
      if (featuresBuilder_ == null) {
        features_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        featuresBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .com.inmind.idmg.schoolnormalize.rpc.NormalFeature features = 1;</code>
     */
    public Builder removeFeatures(int index) {
      if (featuresBuilder_ == null) {
        ensureFeaturesIsMutable();
        features_.remove(index);
        onChanged();
      } else {
        featuresBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .com.inmind.idmg.schoolnormalize.rpc.NormalFeature features = 1;</code>
     */
    public com.inmind.idmg.schoolnormalize.rpc.NormalFeature.Builder getFeaturesBuilder(
        int index) {
      return getFeaturesFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .com.inmind.idmg.schoolnormalize.rpc.NormalFeature features = 1;</code>
     */
    public com.inmind.idmg.schoolnormalize.rpc.NormalFeatureOrBuilder getFeaturesOrBuilder(
        int index) {
      if (featuresBuilder_ == null) {
        return features_.get(index);  } else {
        return featuresBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .com.inmind.idmg.schoolnormalize.rpc.NormalFeature features = 1;</code>
     */
    public java.util.List<? extends com.inmind.idmg.schoolnormalize.rpc.NormalFeatureOrBuilder> 
         getFeaturesOrBuilderList() {
      if (featuresBuilder_ != null) {
        return featuresBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(features_);
      }
    }
    /**
     * <code>repeated .com.inmind.idmg.schoolnormalize.rpc.NormalFeature features = 1;</code>
     */
    public com.inmind.idmg.schoolnormalize.rpc.NormalFeature.Builder addFeaturesBuilder() {
      return getFeaturesFieldBuilder().addBuilder(
          com.inmind.idmg.schoolnormalize.rpc.NormalFeature.getDefaultInstance());
    }
    /**
     * <code>repeated .com.inmind.idmg.schoolnormalize.rpc.NormalFeature features = 1;</code>
     */
    public com.inmind.idmg.schoolnormalize.rpc.NormalFeature.Builder addFeaturesBuilder(
        int index) {
      return getFeaturesFieldBuilder().addBuilder(
          index, com.inmind.idmg.schoolnormalize.rpc.NormalFeature.getDefaultInstance());
    }
    /**
     * <code>repeated .com.inmind.idmg.schoolnormalize.rpc.NormalFeature features = 1;</code>
     */
    public java.util.List<com.inmind.idmg.schoolnormalize.rpc.NormalFeature.Builder> 
         getFeaturesBuilderList() {
      return getFeaturesFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.inmind.idmg.schoolnormalize.rpc.NormalFeature, com.inmind.idmg.schoolnormalize.rpc.NormalFeature.Builder, com.inmind.idmg.schoolnormalize.rpc.NormalFeatureOrBuilder> 
        getFeaturesFieldBuilder() {
      if (featuresBuilder_ == null) {
        featuresBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            com.inmind.idmg.schoolnormalize.rpc.NormalFeature, com.inmind.idmg.schoolnormalize.rpc.NormalFeature.Builder, com.inmind.idmg.schoolnormalize.rpc.NormalFeatureOrBuilder>(
                features_,
                ((bitField0_ & 0x00000001) == 0x00000001),
                getParentForChildren(),
                isClean());
        features_ = null;
      }
      return featuresBuilder_;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest)
  }

  // @@protoc_insertion_point(class_scope:com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest)
  private static final com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest();
  }

  public static com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<NormalizeRequest>
      PARSER = new com.google.protobuf.AbstractParser<NormalizeRequest>() {
    public NormalizeRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new NormalizeRequest(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<NormalizeRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<NormalizeRequest> getParserForType() {
    return PARSER;
  }

  public com.inmind.idmg.schoolnormalize.rpc.NormalizeRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

