/**
 * @format
 */

import {AppRegistry} from 'react-native'
import React from 'react';
import {name as appName} from './app.json'

/* import {Welcome,
    Login,
    Registration,
    ForgetPassword,
    Verification,
    ResetPassword,
    Settings,
    GroupChat,
    Friends,
} from './src/screens'
import UITab from './src/navigation/UITab' */

import App from './src/navigation/App'

AppRegistry.registerComponent(appName,() => () => <App />)