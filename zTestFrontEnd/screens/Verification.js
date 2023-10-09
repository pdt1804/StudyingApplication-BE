import React, {useState} from 'react';
import {
  Text,
  View,
  Image,
  Alert,
  TextInput,
  TouchableOpacity,
  KeyboardAvoidingView,
  Platform,
} from 'react-native';
import {images, colors, fontSizes} from '../constants/index';

const Verification = props => {
  return (
    <KeyboardAvoidingView
      style={{
        backgroundColor: '#D7FFFD',
        flex: 100,
      }}>
      <View
        style={{
          flex: 30,
          backgroundColor: null,
        }}></View>

      <View
        style={{
          flex: 50,
          width: '100%',
          backgroundColor: null,
        }}>
        <View
          style={{
            flex: 8,
            flexDirection: null,
            width: '90%',
            backgroundColor: null,
            alignSelf: 'center',
          }}>
          <Text
            style={{
              color: 'black',
              fontSize: fontSizes.h1,
              fontWeight: 'bold',
            }}>
            Verification code
          </Text>
        </View>

        <View 
          style={{
            flex: 10,
            width: '90%',
            borderColor: 'gray',
            borderWidth: 2,
            borderRadius: 50,
            backgroundColor: 'rgba(250,250,250,0.8)',
            alignSelf: 'center',
          }}>
          <View /* username */
            style={{
              flexDirection: 'row',
              marginHorizontal: 15,
              marginTop: 30,
              borderColor: 'gray',
              borderWidth: 2,
              borderRadius: 20,
              backgroundColor: null,
              alignItems: 'center',
            }}>
            <TextInput
              placeholder="Enter your Verification code"
              placeholderTextColor={colors.placeholder}
            />
          </View>
          

          <TouchableOpacity
            onPress={() => {
              alert('Enter Login Screen');
            }}
            style={{
              marginHorizontal: 55,
              marginTop: 20,
              marginBottom: 5,
              borderColor: 'gray',
              borderWidth: 2,
              borderRadius: 30,
              backgroundColor: 'orange',
              justifyContent: 'center',
              alignItems: 'center',
            }}>
            <Text
              style={{
                padding: 11,
                fontSize: fontSizes.h2,
                fontWeight: 'bold',
              }}>
              {'continue'.toUpperCase()}
            </Text>
          </TouchableOpacity>
        </View>
      </View>

      <View
        style={{
          flex: 20,
          backgroundColor: null,
        }}></View>
    </KeyboardAvoidingView>
  );
};

export default Verification;
