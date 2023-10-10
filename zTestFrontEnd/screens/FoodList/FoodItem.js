import React, {useState, useEffect} from 'react';
import {
    Text, 
    View,
    Image,
    ImageBackground,
    TouchableOpacity,
    TextInput,
    KeyboardAvoidingView,
    Keyboard
} from 'react-native'
import {images, colors, icons, fontSizes} from '../../constants'

function _getColorFromStatus(status) {
    /*
    if(status.toLowerCase().trim() == 'opening now') {
        return 'black'
    } else if(status.toLowerCase().trim() == 'closing soon') {
        return 'green'
    } else if(status.toLowerCase().trim() == 'comming soon') {
        return 'red'
    }
    return 'black'
    */
    return status.toLowerCase().trim() == 'opening now' ? 'black' :
            (status.toLowerCase().trim() == 'closing soon' ? 'green' : 
            (status.toLowerCase().trim() == 'comming soon' ? 'red' : 'black'))
}
function FoodItem(props) {
    let { 
        name, 
        price, 
        socialNetworks, 
        status, 
        url, 
        website,         
    } = props.food //destructuring an object    
    const {onPress} = props
    debugger
    return ( <TouchableOpacity 
        onPress={onPress}
        style={{
        height: 150,                 
        paddingTop: 20,
        paddingStart: 10,
        flexDirection: 'row'
    }}>
        <Image 
            style={{
                width: 100, 
                height: 100,
                resizeMode: 'cover',
                borderRadius: 10,
                marginRight: 15
            }}
            source={{
                uri: url
        }} />
        <View style={{                    
            flex: 1,
            marginRight: 10
        }}>
            <Text style={{
                color: 'black',
                fontSize: fontSizes.h6,
                fontWeight: 'bold'
            }}>{name}</Text>
            <View style={{
                height: 1,
                backgroundColor: 'black',                        
            }} />
            
            <View style={{flexDirection: 'row'}}>
                <Text style={{
                    color: 'cyan',
                    fontSize: fontSizes.h6,
                }}>Status: </Text>
                <Text style={{
                    color: _getColorFromStatus(status),
                    fontSize: fontSizes.h6,
                }}>{status.toUpperCase()}</Text>
            </View>
            <Text style={{
                    color: 'cyan',
                    fontSize: fontSizes.h6,
            }}>Price: {price} $</Text>
            <Text style={{
                    color: 'cyan',
                    fontSize: fontSizes.h6,
            }}>Food Type: Pizza</Text>
            <Text style={{
                    color: 'cyan',
                    fontSize: fontSizes.h6,
            }}>Website: {website}</Text>
            <View style={{
                flexDirection: 'row'
            }}>
                {socialNetworks['facebook'] != undefined  && <Image 
                    source={images.personIcon}
                    style={{
                        paddingEnd: 5,
                        width: 18,
                        height: 18,
                    }}
                    /**name='facebook' size={18} */
                />}
                {socialNetworks['twitter'] != undefined  && <Image 
                    source={images.personIcon}
                    style={{
                        paddingEnd: 5,
                        width: 18,
                        height: 18,
                    }}
                />}
                {socialNetworks['instagram'] != undefined  && <Image 
                    source={images.personIcon}
                    style={{
                        paddingEnd: 5,
                        width: 18,
                        height: 18,
                    }}
                />}
            </View>
        </View>                
    </TouchableOpacity>)
}
export default FoodItem