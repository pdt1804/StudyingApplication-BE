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
import MessengerGroupItems from './MessengerGroupItems';
import {images, colors, fontSizes} from '../../constants';
import {UIHeader} from '../../components';
import Verification from '../Verification';

function MessengerGroup(props) {
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
    {
      imageUrl: 'https://i.pravatar.cc/500',
      isSender: true,
      message: 'Whats your favorite TV show?',
      timestamp: 1700098383,
    },
    {
      imageUrl: 'https://i.pravatar.cc/500',
      isSender: true,
      message: 'Whats your favorite movie?',
      timestamp: 1700101983,
    },
    {
      imageUrl: 'https://i.pravatar.cc/500',
      isSender: true,
      message: 'Whats your favorite book?',
      timestamp: 1700105583,
    },
    {
      imageUrl: 'https://i.pravatar.cc/500',
      isSender: true,
      message: 'What are you doing this weekend?',
      timestamp: 1700109183,
    },
    {
      imageUrl: 'https://i.pravatar.cc/500',
      isSender: true,
      message: 'Are you a morning person or a night person?',
      timestamp: 1700112783,
    },
  ]);

  //list of tabs = state
  const [navigateTab, setNavigateTab] = useState('0');
  const [chatTab, setChatTab] = useState([
    {
      ID: '0',
      name: 'Trò chuyện',
      usedByLeaderOnly: false,
    },
    {
      ID: '1',
      name: 'Nhóm trưởng',
      usedByLeaderOnly: true,
    },
    {
      ID: '2',
      name: 'Thảo luận',
      usedByLeaderOnly: false,
    },
    {
      ID: '3',
      name: 'Thông báo',
      usedByLeaderOnly: false,
    },
  ]);

  //Check if user is Leader or not
  const [isLeader, setIsLeader] = useState(true);

  const {imageUrl, name} = props.route.params.user;

  //navigation
  const {navigation, route} = props;
  //function of navigation to/back
  const {navigate, goBack} = navigation;

  //filter tabs (if isLeader then show all)
  const filteredChatTabs = () =>
    chatTab.filter(eachTab => eachTab.usedByLeaderOnly == false);

  return (
    <View style={{flex: 1, backgroundColor: 'white'}}>
      <UIHeader
        title={name}
        leftIconName={images.backIcon}
        rightIconName={images.globeIcon}
        onPressLeftIcon={() => {
          goBack();
        }}
        onPressRightIcon={() => {
          isLeader ? setIsLeader(false) : setIsLeader(true);
        }}
      />

      <View /* Tabs */
        style={{
          height: 50,
        }}>
        <FlatList
          horizontal={true}
          data={isLeader ? chatTab : filteredChatTabs()}
          renderItem={({item}) => {
            return (
              <TouchableOpacity
                onPress={() => {
                  setNavigateTab(item.ID);
                }}
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

      <View
        style={{
          flex: 1,
        }}>
        {navigateTab == '0' ? (
          <ScrollView /* Chat */>
            {chatHistory.map(eachItem => (
              <MessengerGroupItems item={eachItem} key={eachItem.timestamp} />
            ))}
          </ScrollView>
        ) : navigateTab == '1' ? (
          <View>
            <Text>Hello W</Text>
          </View>
        ) : navigateTab == '2' ? (
          <View>
            <Text>Hello kkkk</Text>
          </View>
        ) : (
          <View>
            <Text>Hello cbnmmmmmm</Text>
          </View>
        )}
      </View>
    </View>
  );
}
export default MessengerGroup;
