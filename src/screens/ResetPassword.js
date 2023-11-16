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
import {UIButton} from '../components/index';
import {isValidEmail, isValidPassword} from '../utilies/Validation';

const ResetPassword = props => {
  //states for validating
  const [errorEmail, setErrorEmail] = useState('');
  const [errorPassword, setErrorPassword] = useState('');
  //states to store email/password
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  //navigation
  const {navigation, route} = props;
  //function of navigation to/back
  const {navigate, goBack} = navigation;

  return (
    <KeyboardAvoidingView
      behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
      style={{
        backgroundColor: '#D7FFFD',
        flex: 100,
      }}>
      <View
        style={{
          flex: 15,
          backgroundColor: null,
        }}></View>

      <View
        style={{
          flex: 60,
          flexDirection: null,
          width: '100%',
          backgroundColor: null,
        }}>
        <View
          style={{
            flex: 20,
            flexDirection: null,
            width: '95%',
            backgroundColor: null,
            alignSelf: 'center',
          }}>
          <Text
            style={{
              color: 'black',
              fontSize: fontSizes.h1,
              fontWeight: 'bold',
            }}>
            ResetPassword!!
          </Text>
        </View>

        <View
          style={{
            flex: 80,
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
              marginVertical: 40,
              borderColor: 'gray',
              borderWidth: 2,
              borderRadius: 20,
              backgroundColor: null,
              alignItems: 'center',
            }}>
            <Image
              source={images.keyIcon}
              style={{
                width: 25,
                height: 25,
              }}
            />
            <TextInput
              onChangeText={text => {
                /*
                if (isValidEmail(text) == false) {
                  setErrorEmail('Email not in correct format');
                } else setErrorEmail('');
              */
                setErrorEmail(
                  isValidEmail(text) == true
                    ? ''
                    : 'Email not in correct format',
                );
                setEmail(text);
              }}
              placeholder="Password"
              placeholderTextColor={colors.placeholder}
            />
          </View>

          <View /* password */
            style={{
              flexDirection: 'row',
              marginHorizontal: 15,
              borderColor: 'gray',
              borderWidth: 2,
              borderRadius: 20,
              backgroundColor: null,
              alignItems: 'center',
            }}>
            <Image
              source={images.keyIcon}
              style={{
                width: 25,
                height: 25,
              }}
            />
            <TextInput
              onChangeText={text => {
                setErrorPassword(
                  isValidPassword(text) == true
                    ? ''
                    : 'Password must be at least 3 characters',
                );
                setPassword(text);
              }}
              placeholder="Re-enter Password"
              placeholderTextColor={colors.placeholder}
            />
          </View>

          <TouchableOpacity
            onPress={() => {
              navigate('Login');
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
                fontSize: fontSizes.h4,
                fontWeight: 'bold',
              }}>
              {'ResetPassword'.toUpperCase()}
            </Text>
          </TouchableOpacity>
        </View>
      </View>

      <View
        style={{
          flex: 15,
          backgroundColor: null,
        }}></View>
    </KeyboardAvoidingView>
  );
};

export default ResetPassword;
