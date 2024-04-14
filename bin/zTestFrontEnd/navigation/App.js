import React, {Component, useState} from 'react';
import {NavigationContainer} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import {createBottomTabNavigator} from '@react-navigation/bottom-tabs';
import {StackRouter} from 'react-navigation';
import {
  Welcome,
  Login,
  Registration,
  ForgetPassword,
  Verification,
  ResetPassword,
  Messenger,
} from '../screens';
import UITab from './UITab';

const Stack = createNativeStackNavigator();

function App(props) {
  return (
    <NavigationContainer>
      <Stack.Navigator
        initialRouteName="UITab"
        screenOptions={{
          headerShown: false,
        }}>
        <Stack.Screen name="Welcome" component={Welcome} />
        <Stack.Screen name="Login" component={Login} />
        <Stack.Screen name="Registration" component={Registration} />
        
        <Stack.Screen name="ForgetPassword" component={ForgetPassword} />
        <Stack.Screen name="Verification" component={Verification} />
        <Stack.Screen name="ResetPassword" component={ResetPassword} />

        <Stack.Screen name="UITab" component={UITab} />
        <Stack.Screen name="Messenger" component={Messenger} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}
export default App;
