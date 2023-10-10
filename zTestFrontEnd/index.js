/**
 * @format
 */

import {AppRegistry} from 'react-native'
import {name as appName} from './app.json'

//import MainScreen from './screens/MainScreen';
import {Welcome,
    Login,
    Registration,
    ForgetPassword,
    Verification,
    ResetPassword,
    Settings,
} from './screens'

AppRegistry.registerComponent(appName, () => Settings)