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

function _getIconFromStatus(status) {
  if (status.toLowerCase().trim() == 'online') {
    return images.checkMarkIcon;
  } else return images.cancelIcon;
}

function GroupChatItems(props) {
  let {name, imageUrl, newestMessage, status} = props.group;
  const {onPress} = props;

  let fontSizeName = fontSizes.h3;
  if (name.length > 22) {
    fontSizeName = fontSizes.h5;
  }

  return (
    <TouchableOpacity
      onPress={onPress}
      style={{
        height: 90,
        paddingTop: 20,
        paddingStart: 10,
        flexDirection: 'row',
        alignItems: 'center',
      }}>
      <View>
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
        <Image /** Status */
          style={{
            width: 17,
            height: 17,
            resizeMode: 'cover',
            borderRadius: 90,
            marginRight: 15,
            position: 'absolute',
            right: 0,
            bottom: 0,
            tintColor: _getColorFromStatus(status),
          }}
          source={_getIconFromStatus(status)}
        />
      </View>

      <View
        style={{
          flexDirection: 'column',
          flex:4,
          marginRight: 10,
        }}>
        <Text /** Name */
          style={{
            color: 'black',
            fontSize: fontSizeName,
            fontWeight: 'bold',
          }}>
          {name}
        </Text>
        <Text /** Message */
          style={{
            color: colors.inactive,
            fontSize: fontSizes.h6,
          }}>
          {/**newestMessage*/ 'Hello, Àm Fai Thank You, Àn you?'}
        </Text>
      </View>

      <View
        style={{
          flexDirection: 'column',
          flex:1,
          justifyContent: 'center',
          alignItems: 'flex-end',
        }}>
        <Text /** Minute ago */
          style={{
            color: 'black',
            fontSize: fontSizes.h6 * 0.8,
            marginRight: 10,
          }}>
          5 phút trước
        </Text>
      </View>
    </TouchableOpacity>
  );
}
export default GroupChatItems;
