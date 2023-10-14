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
import GroupChatItems from './GroupChatItems';

function GroupChat(props) {
  //list of group example = state
  const [groups, setGroups] = useState([
    {
      ID: '01',
      name: 'Top 30 Nhạc Remix Tiktok Hay Nhất 2022',
      imageUrl:
        'https://i1.sndcdn.com/avatars-000528843336-cug73s-t500x500.jpg',
      status: 'Online',
    },
    {
      ID: '02',
      name: 'Anti giản viên ABC-XXX',
      imageUrl:
        'https://jekashop.co.uk/media/catalog/product/cache/12ad95d8a2fb3df88ee5f5df1ef6c6e8/a/n/anti-slip-picto-d-p006.png',
      status: 'Offline',
    },
    {
      ID: '03',
      name: 'Discover the world',
      imageUrl:
        'https://m.media-amazon.com/images/I/61NV+pGB42L._AC_UF1000,1000_QL80_.jpg',
      status: 'Offline',
    },
    {
      ID: '04',
      name: 'SE121.O11.PMCL',
      imageUrl:
        'https://cdn.haitrieu.com/wp-content/uploads/2021/10/Logo-DH-Cong-Nghe-Thong-Tin-UIT.png',
      status: 'Online',
    },
    {
      ID: '05',
      name: 'Nhóm học tập trẩu tre',
      imageUrl: 'https://i.imgur.com/WnXdJha.png',
      status: 'Offline',
    },
  ]);

  return (
    <View style={{flex: 1, backgroundColor: 'white'}}>
      <ScrollView>
        {groups.map(eachGroup => (
          <GroupChatItems
            group={eachGroup}
            key={eachGroup.ID}
            onPress={() => {
              alert(`You pressed group "${eachGroup.name}"`);
            }}
          />
        ))}
      </ScrollView>
    </View>
  );
}
export default GroupChat;
