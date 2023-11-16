import React, {useState, useEffect} from 'react';
import {
  Text,
  View,
  Image,
  TouchableOpacity,
  TextInput,
  FlatList,
  SafeAreaView,
} from 'react-native';
import {images, colors, icons, fontSizes} from '../../constants';

function NotificationItems(props) {
  let {name, imageUrl, status} = props.group;
  const {onPress} = props;
  return (
    <TouchableOpacity
      onPress={onPress}
      style={{
        height: 100,
        paddingTop: 20,
        paddingStart: 10,
        flexDirection: 'row',
      }}>
      <Image
        style={{
          width: 55,
          height: 55,
          resizeMode: 'cover',
          borderRadius: 90,
          marginRight: 15,
        }}
        source={images.atSignIcon}
      />
      <View
        style={{
          flex: 1,
          marginRight: 10,
        }}>
        <Text
          style={{
            color: 'black',
            fontSize: fontSizes.h2,
            fontWeight: 'bold',
          }}>
          {name}
        </Text>
      </View>
    </TouchableOpacity>
  );
}
export default NotificationItems;
