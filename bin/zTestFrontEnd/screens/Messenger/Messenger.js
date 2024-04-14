import React, {useState, useEffect} from 'react';
import {
  Text,
  View,
  Image,
  TouchableOpacity,
  TextInput,
  FlatList,
  SafeAreaView,
  ScrollView,
  Alert,
} from 'react-native';
import MessengerItems from './MessengerItems';
import {images, colors, fontSizes} from '../../constants';
import {UIHeader} from '../../components';
import Verification from '../Verification';

function Messenger(props) {
  //list of example = state
  const [chatHistory, setChatHistory] = useState([
    {
      imageUrl: 'https://i.pravatar.cc/500',
      isSender: true,
      message: 'Hello.',
      timestamp: 1668135552,
    },
    {
      imageUrl: 'https://i.pravatar.cc/505',
      isSender: false,
      message: 'Hi. How are you?',
      timestamp: 1696993152,
    },
    {
      imageUrl: 'https://i.pravatar.cc/500',
      isSender: true,
      message: 'Im fine thank you, and you?',
      timestamp: 1699585152,
    },
    {
      imageUrl: 'https://i.pravatar.cc/505',
      isSender: false,
      message: 'No.',
      timestamp: 1699667952,
    },
    {
      imageUrl: 'https://i.pravatar.cc/505',
      isSender: false,
      message: 'Im in heaven.',
      timestamp: 1699671552,
    },
  ]);

  //list of tabs = state
  const [navigateTab, setNavigateTab] = useState('')
  const [chatTab, setChatTab] = useState([
    {
      ID: '0',
      name: 'Trò chuyện',
    },
    {
      ID: '1',
      name: 'Nhóm trưởng',
    },
    {
      ID: '2',
      name: 'Thảo luận',
    },
    {
      ID: '3',
      name: 'Thông báo',
    },
  ]);

  const {imageUrl, name} = props.route.params.user;

  //navigation
  const {navigation, route} = props;
  //function of navigation to/back
  const {navigate, goBack} = navigation;

  return (
    <View style={{flex: 1, backgroundColor: 'white'}}>
      <UIHeader
        title={name}
        leftIconName={images.backIcon}
        rightIconName={images.pencilIcon}
        onPressLeftIcon={() => {
          /* alert('To the previous screen'); */
          goBack();
        }}
        onPressRightIcon={() => {
          alert('Edit profile');
        }}
      />

      <View
        style={{
          height: 50,
        }}>
        <FlatList
          horizontal={true}
          data={chatTab}
          renderItem={({item}) => {
            return (
              <TouchableOpacity
                onPress={() => setNavigateTab(item.ID)}
                style={{
                  padding: 1,
                  flexDirection: 'row',
                  alignItems: 'center',
                }}>
                <Text
                  style={{
                    color: 'black',
                    fontSize: fontSizes.h6,
                    paddingVertical: 7,
                    paddingHorizontal: 17,
                    backgroundColor: colors.message,
                    borderRadius: 3,
                  }}>
                  {item.name}
                </Text>
              </TouchableOpacity>
            );
          }}
          keyExtractor={item => item.name}
          style={{flex: 1}}></FlatList>
      </View>

      <ScrollView>
        {chatHistory.map(eachItem => (
          <MessengerItems item={eachItem} />
        ))}
      </ScrollView>
    </View>
  );
}
export default Messenger;
