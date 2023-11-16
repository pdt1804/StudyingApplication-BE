import React, {Component} from 'react';
import {TouchableOpacity, Text, View, Image} from 'react-native';
import {images, colors, fontSizes} from '../constants';

function UIHeader(props) {
  const {
    title,
    leftIconName,
    rightIconName,
    onPressLeftIcon,
    onPressRightIcon,
  } = props;

  return (
    <View
      style={{
        height: 55,
        backgroundColor: colors.primary,
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
      }}>
      {leftIconName != undefined ? (
        <TouchableOpacity
          style={{
            width: 50,
            height: 50,
            flexDirection: 'row',
            alignItems: 'center',
          }}
          onPress={onPressLeftIcon}>
          <Image
            source={leftIconName}
            style={{
              width: 35,
              height: 35,
              marginStart: 10,
            }}
          />
        </TouchableOpacity>
      ) : (
        <View style={{width: 50, height: 50}} />
      )}
      <Text
        style={{
          fontWeight: 'bold',
          fontSize: fontSizes.h2,
          alignSelf: 'center',
          lineHeight: 45,
          color: 'black',
        }}>
        {title}
      </Text>
      {rightIconName != undefined ? (
        <TouchableOpacity
          style={{
            width: 50,
            height: 50,
            flexDirection: 'row',
            alignItems: 'center',
          }}
          onPress={onPressRightIcon}>
          <Image
            source={rightIconName}
            style={{
              width: 35,
              height: 35,
              marginEnd: 10,
            }}
          />
        </TouchableOpacity>
      ) : (
        <View style={{width: 50, height: 50}} />
      )}
    </View>
  );
}
export default UIHeader;
