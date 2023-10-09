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
} from './screens'

AppRegistry.registerComponent(appName, () => Login)