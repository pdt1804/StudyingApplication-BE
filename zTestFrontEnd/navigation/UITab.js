/**
 * yarn add react-native-vector-icons
 *
 * >>Prepare...
 * yarn add react-navigation
 * yarn add react-native-safe-area-context
 *
 * >>Bottom tabs
 * yarn add @react-navigation/bottom-tabs
 *
 * >> Next/Back
 * yarn add @react-navigation/native
 * yarn add @react-navigation/native-stack
 *
 * >>Don't know why but it solve the error
 * yarn add react-native-screens
 */

import * as React from 'react';
import {
  Text,
  View,
  Image,
  Alert,
  TextInput,
  TouchableOpacity,
  KeyboardAvoidingView,
  Platform,
} from 'react-native';
import {Settings, GroupChat, Friends, AllNotification} from '../screens';
import {createBottomTabNavigator} from '@react-navigation/bottom-tabs';
import {images, colors, fontSizes} from '../constants';

const Tab = createBottomTabNavigator();

const ScreenOptions = ({route}) => ({
  headerShown: false,
  tabBarActiveTintColor: colors.active,
  tabBarInactiveTintColor: colors.inactive,
  tabBarActiveBackgroundColor: colors.myWhite,
  tabBarInactiveBackgroundColor: colors.myWhite,

  tabBarIcon: ({focused, color, size}) => {
    let screenName = route.name;
    let iconName = images.personIcon;
    if (screenName == 'GroupChat') {
      iconName = images.groupIcon;
    } else if (screenName == 'Friends') {
      iconName = images.chatIcon;
    } else if (screenName == 'Notifications') {
      iconName = images.bellIcon;
    }

    return (
      <Image
        source={iconName}
        style={{
          width: 22,
          height: 22,
          tintColor: focused ? colors.active : colors.inactive,
        }}
      />
    );
  },
});

function UITab(props) {
  const fontSizeTabs = fontSizes.h6;

  return (
    <Tab.Navigator initialRouteName="Settings" screenOptions={ScreenOptions}>
      <Tab.Screen
        name="Settings"
        component={Settings}
        options={{
          tabBarLabel: 'Tài khoản',
          tabBarLabelStyle: {
            fontSize: fontSizeTabs,
          },
        }}
      />
      <Tab.Screen
        name="Friends"
        component={Friends}
        options={{
          tabBarLabel: 'Bạn bè',
          tabBarLabelStyle: {
            fontSize: fontSizeTabs,
          },
        }}
      />
      <Tab.Screen
        name="GroupChat"
        component={GroupChat}
        options={{
          tabBarLabel: 'Nhóm học tập',
          tabBarLabelStyle: {
            fontSize: fontSizeTabs,
          },
        }}
      />
      <Tab.Screen
        name="Notifications"
        component={AllNotification}
        options={{
          tabBarLabel: 'Thông báo',
          tabBarLabelStyle: {
            fontSize: fontSizeTabs,
          },
        }}
      />
    </Tab.Navigator>
  );
}
export default UITab;
