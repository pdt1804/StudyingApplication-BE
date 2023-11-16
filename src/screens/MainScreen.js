
import React from 'react';
import {Text, View} from 'react-native'

import {sum2Number, subtract2Number, PI} from '../utilies/Calculation'

// component = function
/*
function MainScreen(props)
{
    return <Text>This is MainScreen</Text>
}
*/
//Or create a variable which reference to a function
const MainScreen = (props) => {
    return <View>
        <Text>This is sdfgivhsdfiyg</Text>
        <Text>Sum 2 and 3 = {sum2Number(2,3)}</Text>
        <Text>Subtract 2 and 3 = {subtract2Number(2,3)}</Text>
        <Text>ppi = {PI}</Text>
    </View>
}

export default MainScreen