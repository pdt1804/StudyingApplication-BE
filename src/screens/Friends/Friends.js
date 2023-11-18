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
import FriendItems from './FriendItems';
import {images, colors, fontSizes} from '../../constants';
import {UIHeader} from '../../components';

function Friends(props) {
  //list of group example = state
  const [groups, setGroups] = useState([
    {
      ID: '01',
      name: 'Tom',
      imageUrl: 'https://i.pravatar.cc/1000',
    },
    {
      ID: '02',
      name: 'Jerry',
      imageUrl: 'https://i.pravatar.cc/1001',
    },
    {
      ID: '03',
      name: 'Edison',
      imageUrl: 'https://i.pravatar.cc/1002',
    },
    {
      ID: '04',
      name: 'Anh So Tanh',
      imageUrl: 'https://i.pravatar.cc/1003',
    },
    {
      ID: '05',
      name: 'My dog',
      imageUrl: 'https://i.pravatar.cc/1004',
    },
  ]);

  //use for search bar (textInput)
  const [searchText, setSearchText] = useState('');

  //navigation
  const {navigation, route} = props;
  //function of navigation to/back
  const {navigate, goBack} = navigation;

  return (
    <View style={{flex: 1, backgroundColor: 'white'}}>
      <UIHeader
        title={'Bạn bè'}
        leftIconName={null}
        rightIconName={null}
        onPressLeftIcon={() => {}}
        onPressRightIcon={() => {}}
      />

      <View
        style={{
          marginHorizontal: 15,
          flexDirection: 'row',
          paddingTop: 10,
        }}>
        <TextInput
          autoCorrect={false}
          onChangeText={text => {
            setSearchText(text);
          }}
          style={{
            backgroundColor: 'gray',
            height: '75%',
            flex: 1,
            borderRadius: 90,
            paddingStart: 35,
          }}
        />
        <Image
          source={images.searchIcon}
          style={{
            width: 20,
            height: 20,
            position: 'absolute',
            top: 18,
            left: 8,
          }}
        />
      </View>

      <View style={{backgroundColor: 'black', height: 1}} />

      <ScrollView>
        {groups
          .filter(eachGroup =>
            eachGroup.name.toLowerCase().includes(searchText.toLowerCase()),
          )
          .map(eachGroup => (
            <FriendItems
              group={eachGroup}
              key={eachGroup.ID}
              onPress={() => {
                navigate('Messenger', {user: eachGroup});
              }}
            />
          ))}
      </ScrollView>
    </View>
  );
}
export default Friends;
