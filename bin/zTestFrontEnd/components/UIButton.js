import React, {Component} from 'react';
import {Text, TouchableOpacity} from 'react-native'
import {images,colors} from '../constants/index'


function UIButton(props) {
    const {onPress, title, isSelected} = props
    return <TouchableOpacity
    onPress={onPress}
    style = {{
        borderColor: colors.PrimaryBorder,
        borderWidth: 2,
        width: '70%',
        height: 45,
        borderRadius: 5,
        marginHorizontal: 15,
        marginVertical: 10,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: isSelected == true ? colors.PrimaryBackgroundButton : null
    }}>
        <Text style = {{
            color: 'black'
        }}>{title}</Text>
    </TouchableOpacity>
}

export default UIButton