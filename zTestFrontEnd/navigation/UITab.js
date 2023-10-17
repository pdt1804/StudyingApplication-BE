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

import * as React from 'react'
import {Settings,GroupChat,Login} from '../screens'
import {createBottomTabNavigator} from '@react-navigation/bottom-tabs'
import {images,colors,fontSizes} from '../constants'

const Tab = createBottomTabNavigator()

function UITab(props){
    return <Tab.Navigator initialRouteName='Settings' screenOptions={{
        headerShown: false
    }}>
        <Tab.Screen name='GroupChat' component={GroupChat}/>
        <Tab.Screen name='Login' component={Login}/>
        <Tab.Screen name='Settings' component={Settings}/>
    </Tab.Navigator>
}
export default UITab