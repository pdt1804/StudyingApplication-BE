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

const Registration = props => {
  //states for validating
  const [errorEmail, setErrorEmail] = useState('');
  const [errorPassword, setErrorPassword] = useState('');
  //states to store email/password
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  return (
    <KeyboardAvoidingView
      behavior={Platform.OS === "ios" ? "padding" : "height"}
      style={{
        backgroundColor: '#D7FFFD',
        flex: 100,
      }}>
      <View
        style={{
          flex: 10,
          backgroundColor: null,
        }}></View>

      <View
        style={{
          flex: 70,
          width: '100%',
          backgroundColor: null,
        }}>
        <View
          style={{
            flex: 10,
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
            SIGN UP
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
              marginTop: 30,
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
              }}
            />
            <TextInput
              placeholder="Username"
              placeholderTextColor={colors.placeholder}
            />
          </View>
          
          <View /* email */
            style={{
              flexDirection: 'row',
              marginHorizontal: 15,
              marginTop: 15,
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
              }}
            />
            <TextInput
              placeholder="Email"
              placeholderTextColor={colors.placeholder}
            />
          </View>
          
          <View /* password */
            style={{
              flexDirection: 'row',
              marginHorizontal: 15,
              marginTop: 15,
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
              placeholder="Password"
              placeholderTextColor={colors.placeholder}
            />
          </View>
          
          <View /* re-enter password */
            style={{
              flexDirection: 'row',
              marginHorizontal: 15,
              marginTop: 15,
              marginBottom: 35,
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
              placeholder="Re-enter Password"
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
              {'Sign up'.toUpperCase()}
            </Text>
          </TouchableOpacity>

          <TouchableOpacity
            onPress={() => {
              alert('Enter login Screen');
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
                fontSize: fontSizes.h4,
                fontWeight: 'bold',
                color: 'blue',
              }}>
              Already have a Account? Login
            </Text>
          </TouchableOpacity>
        </View>
      </View>

      <View
        style={{
          flex: 5,
          backgroundColor: null,
        }}></View>
    </KeyboardAvoidingView>
  );
};

export default Registration;
