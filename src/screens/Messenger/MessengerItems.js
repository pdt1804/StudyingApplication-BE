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

function MessengerItems(props) {
  let {imageUrl, isSender, message, timestamp} = props.item;
  const {onPress} = props;

  return isSender == false ? (
    <View
      style={{
        height: 90,
        paddingTop: 20,
        paddingStart: 10,
        flexDirection: 'row',
        alignItems: 'center',
      }}>
      <Image /** Avatar */
        style={{
          width: 55,
          height: 55,
          resizeMode: 'cover',
          borderRadius: 90,
          marginRight: 15,
          alignSelf: 'center',
        }}
        source={{
          uri: imageUrl,
        }}
      />

      <View
        style={{
          flexDirection: 'row',
          flex: 1,
          marginRight: 10,
          justifyContent: 'flex-start',
        }}>
        <Text /** Message */
          style={{
            color: 'black',
            fontSize: fontSizes.h6,
            paddingVertical: 7,
            paddingHorizontal: 7,
            backgroundColor: colors.message,
            borderRadius: 10,
          }}>
          {message}
        </Text>
      </View>
    </View>
  ) : (
    <View /** isSender = true */
      style={{
        height: 90,
        paddingTop: 20,
        paddingStart: 10,
        flexDirection: 'row',
        alignItems: 'center',
      }}>
      <View
        style={{
          flexDirection: 'row',
          flex: 1,
          marginRight: 10,
          justifyContent: 'flex-end',
        }}>
        <Text /** Message */
          style={{
            color: 'black',
            fontSize: fontSizes.h6,
            paddingVertical: 7,
            paddingHorizontal: 7,
            backgroundColor: colors.message,
            borderRadius: 10,
          }}>
          {message}
        </Text>
      </View>

      <Image /** Avatar */
        style={{
          width: 55,
          height: 55,
          resizeMode: 'cover',
          borderRadius: 90,
          marginRight: 15,
          alignSelf: 'center',
        }}
        source={{
          uri: imageUrl,
        }}
      />
    </View>
  );
}
export default MessengerItems;
