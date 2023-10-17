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
import {Settings, GroupChat, Login} from '../screens';
import {createBottomTabNavigator} from '@react-navigation/bottom-tabs';
import {images, colors, fontSizes} from '../constants';

const Tab = createBottomTabNavigator();

const ScreenOptions = ({route}) => ({
  headerShown: false,
  tabBarActiveTintColor: colors.active,
  tabBarInactiveTintColor: colors.inactive,
  tabBarActiveBackgroundColor: 'white',
  tabBarInactiveBackgroundColor: 'gold',

  tabBarIcon: ({focused, color, size}) => {
    let screenName = route.name;
    let iconName = images.personIcon;
    if (screenName == 'GroupChat') {
      iconName = images.atSignIcon;
    } else if (screenName == 'Login') {
      iconName = images.globeIcon;
    } else if (screenName == 'Settings') {
      iconName = images.menuIcon;
    }

    return (
      <Image
        source={iconName}
        style={{
          width: 25,
          height: 25,
          marginRight: 10,
          marginLeft: 10,
          tintColor: focused ? colors.active : colors.inactive,
        }}
      />
    );
  },
});

function UITab(props) {
  return (
    <Tab.Navigator initialRouteName="Settings" screenOptions={ScreenOptions}>
      <Tab.Screen
        name="GroupChat"
        component={GroupChat}
        options={{
          tabBarLabel: 'Groups',
          tabBarLabelStyle: {
            fontSize: fontSizes.h3,
          },
        }}
      />
      <Tab.Screen
        name="Login"
        component={Login}
        options={{
          tabBarLabel: 'Testing...',
          tabBarLabelStyle: {
            fontSize: fontSizes.h3,
          },
        }}
      />
      <Tab.Screen
        name="Settings"
        component={Settings}
        options={{
          tabBarLabel: 'Settings',
          tabBarLabelStyle: {
            fontSize: fontSizes.h3,
          },
        }}
      />
    </Tab.Navigator>
  );
}
export default UITab;
