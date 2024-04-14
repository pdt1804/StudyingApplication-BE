/**
 * @format
 */

import {AppRegistry} from 'react-native'
import React from 'react';
import {name as appName} from './app.json'

//import MainScreen from './screens/MainScreen';

import {Welcome,
    Login,
    Registration,
    ForgetPassword,
    Verification,
    ResetPassword,
    Settings,
    GroupChat,
    Friends,
} from './screens'
import UITab from './navigation/UITab'

import App from './navigation/App'

AppRegistry.registerComponent(appName,() => () => <App />)