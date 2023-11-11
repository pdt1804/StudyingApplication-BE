import React, {useEffect, useState} from 'react';
import {
  Text,
  View,
  Image,
  Alert,
  TextInput,
  TouchableOpacity,
  KeyboardAvoidingView,
  Keyboard,
  Platform,
} from 'react-native';
import {images, colors, fontSizes} from '../constants/index';
import {UIButton} from '../components/index';
import {isValidEmail, isValidPassword} from '../utilies/Validation';
import axios from 'axios';

const getUserCredentials = async () => {
  try {
    const response = await axios.get(
      'https://localhost:8080/api/v1/user/Authenticate', {
        params: {
          userName: 'abc',
          passWord: 'abc', 
        }
      }
    );
    alert('1234')

    // The response will contain the username and password.
    const {username, password} = response.data;

    return {username, password};
  } catch (error) {
    // Handle the error.
  }
};

const Login = props => {
  const [keyboardIsShown, setKeyboardIsShown] = useState(false);
  //states for validating
  const [errorEmail, setErrorEmail] = useState('');
  const [errorPassword, setErrorPassword] = useState('');
  //states to store email/password
  /*   const [email, setEmail] = useState('');
  const [password, setPassword] = useState(''); */

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  useEffect(() => {
    const getUserCredentialsAsync = async () => {
      const {username, password} = await getUserCredentials();

      setUsername(username);
      setPassword(password);
    };

    getUserCredentialsAsync();
  }, []);

  //navigation
  const {navigation, route} = props;
  //function of navigation to/back
  const {navigate, goBack} = navigation;

  useEffect(() => {
    //componentDidMount
    Keyboard.addListener('keyboardDidShow', () => {
      setKeyboardIsShown(true);
    });
    Keyboard.addListener('keyboardDidHide', () => {
      setKeyboardIsShown(false);
    });
  });

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
            Welcome!!
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
              source={images.personIcon}
              style={{
                width: 25,
                height: 25,
                marginRight: 10,
                marginLeft: 10,
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
              placeholder="Username"
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
                marginRight: 10,
                marginLeft: 10,
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
              placeholder="Password"
              placeholderTextColor={colors.placeholder}
            />
          </View>

          {keyboardIsShown == false && (
            <TouchableOpacity /* forget password */
              onPress={() => {
                navigate('ForgetPassword');
              }}
              style={{
                marginHorizontal: 5,
                marginBottom: 25,
                backgroundColor: null,
                justifyContent: 'center',
                alignItems: 'flex-end',
              }}>
              <Text
                style={{
                  padding: 1,
                  fontSize: fontSizes.h6,
                  fontWeight: 'bold',
                  color: 'blue',
                }}>
                Forget Password?
              </Text>
            </TouchableOpacity>
          )}

          {keyboardIsShown == false && (
            <TouchableOpacity
              onPress={() => { 
                alert('cbnm'),
                getUserCredentials
                //navigate('UITab');
                //alert(`Email = ${username}, password = ${password}`);
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
                  fontSize: fontSizes.h3,
                  fontWeight: 'bold',
                }}>
                {'Login'.toUpperCase()}
              </Text>
            </TouchableOpacity>
          )}

          {keyboardIsShown == false && (
            <TouchableOpacity
              onPress={() => {
                navigate('Registration');
              }}
              style={{
                marginHorizontal: 55,
                marginBottom: 5,
                backgroundColor: null,
                justifyContent: 'center',
                alignItems: 'center',
              }}>
              <Text
                style={{
                  padding: 11,
                  fontSize: fontSizes.h6,
                  fontWeight: 'bold',
                  color: 'orange',
                }}>
                Don't have a Account? Register
              </Text>
            </TouchableOpacity>
          )}
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

export default Login;
