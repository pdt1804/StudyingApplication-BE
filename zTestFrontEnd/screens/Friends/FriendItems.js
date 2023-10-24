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

function _getColorFromStatus(status) {
  if (status.toLowerCase().trim() == 'online') {
    return colors.Online;
  } else return colors.Offline;
}

function FriendItems(props) {
  let {name, imageUrl, status} = props.group;
  const {onPress} = props;
  
  let fontSizeName = fontSizes.h3;
  if (name.length > 22) {
    fontSizeName = fontSizes.h4;
  }

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
        source={{
          uri: imageUrl,
        }}
      />
      <View
        style={{
          flex: 1,
          marginRight: 10,
        }}>
        <Text
          style={{
            color: 'black',
            fontSize: fontSizeName,
            fontWeight: 'bold',
          }}>
          {name}
        </Text>
        <View
          style={{
            backgroundColor: 'black',
            height: 1,
          }}
        />
      </View>
    </TouchableOpacity>
  );
}
export default FriendItems;
