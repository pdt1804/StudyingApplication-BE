import React from 'react';
import {
    Text,
    View,
    Image,
    Alert
} from 'react-native'
import {images,colors} from '../constants/index'
import {UIButton} from '../components/index'

const Welcome = (props) => {
    return <View style = {{
        backgroundColor: '#D7FFFD',
        flex: 100
    }}>
        <View style = {{
            flex: 20,
            flexDirection: null,            
            width: '100%',
            backgroundColor: null,
        }}></View>

        <View style = {{
            flex: 30,
            flexDirection: 'column',            
            width: '100%',
            alignItems: 'center',
            alignSelf: 'center',
            backgroundColor: null,
        }}>
            <Image 
                source={images.uitLogo}
                style = {{
                    width: 200,
                    height: 200
                }}
            />
        </View>
        
        <View style = {{
            flex: 35,
            flexDirection: 'column',            
            width: '100%',
            alignItems: 'center',
            alignSelf: 'center',
            backgroundColor: null,
        }}>
            <UIButton 
                onPress = {() => {
                    alert('rrr')}}
                title = {'Login'.toUpperCase()}
            ></UIButton>
        </View>
    </View>
}

export default Welcome