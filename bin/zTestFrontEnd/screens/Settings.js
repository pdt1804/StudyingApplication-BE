import React, {useState, useEffect} from 'react';
import {
  Text,
  View,
  Image,
  TouchableOpacity,
  TextInput,
  FlatList,
  ScrollView,
  Switch,
  SafeAreaView,
} from 'react-native';
import {images, colors, icons, fontSizes} from '../constants';
import {UIHeader} from '../components';
function Settings(props) {
  const [isEnabledLockApp, setEnabledLockApp] = useState(true);
  const [isUseFingerprint, setUseFingerprint] = useState(false);
  const [isEnabledChangePassword, setEnabledChangePassword] = useState(true);

  //navigation
  const {navigation, route} = props;
  //function of navigation to/back
  const {navigate, goBack} = navigation;

  return (
    <SafeAreaView
      style={{
        flex: 1,
        backgroundColor: colors.myWhite,
      }}>
      <UIHeader
        title={'Tài khoản'}
        leftIconName={images.backIcon}
        rightIconName={images.pencilIcon}
        onPressLeftIcon={()=>{
          alert('To the previous screen')
        }}
        onPressRightIcon={()=>{
          alert('Edit profile')
        }}
      />

      <ScrollView>
        <View
          style={{
            height: 200,
            //backgroundColor: colors.mainBackground,
            alignItems: 'center',
          }}>
          <Image
            source={images.background}
            style={{
              width: 100,
              height: 100,
              resizeMode: 'cover',
              margin: 15,
              borderRadius: 90,
              borderColor: 'white',
              borderWidth: 5,
            }}
          />
          <Text
            style={{
              color: 'black',
              fontSize: fontSizes.h6,
            }}>
            USERNAME
          </Text>
        </View>

        <View /** Thông tin tài khoản */
          style={{
            height: 50,
            backgroundColor: colors.myWhite,
            justifyContent: 'center',
          }}>
          <Text
            style={{
              fontSize: fontSizes.h7,
              color: colors.noImportantText,
              paddingStart: 10,
            }}>
            Thông tin tài khoản
          </Text>
        </View>
        <View
          style={{
            flexDirection: 'row',
            paddingVertical: 10,
            alignItems: 'center',
          }}>
          <Image
            source={images.phoneIcon}
            style={{
              width: 20,
              height: 20,
              marginStart: 10,
            }}
          />
          <Text
            style={{
              fontSize: fontSizes.h6,
              color: 'black',
              paddingStart: 10,
            }}>
            Số điện thoại
          </Text>
          <View style={{flex: 1}} />
          <Image
            source={images.chevronRightIcon}
            style={{
              width: 20,
              height: 20,
              marginStart: 10,
            }}
          />
        </View>
        <View
          style={{
            flexDirection: 'row',
            paddingVertical: 10,
            alignItems: 'center',
          }}>
          <Image
            source={images.emailIcon}
            style={{
              width: 20,
              height: 20,
              marginStart: 10,
            }}
          />
          <Text
            style={{
              fontSize: fontSizes.h6,
              color: 'black',
              paddingStart: 10,
            }}>
            Email
          </Text>
          <View style={{flex: 1}} />
          <Image
            source={images.chevronRightIcon}
            style={{
              width: 20,
              height: 20,
              marginStart: 10,
            }}
          />
        </View>
        <TouchableOpacity
          style={{
            flexDirection: 'row',
            paddingVertical: 10,
            alignItems: 'center',
          }}
          onPress={() => {
            navigate('Verification');
          }}>
          <Image
            source={images.keyIcon}
            style={{
              width: 20,
              height: 20,
              marginStart: 10,
            }}
          />
          <Text
            style={{
              fontSize: fontSizes.h6,
              color: 'black',
              paddingStart: 10,
            }}>
            Đổi mật khẩu
          </Text>
          <View style={{flex: 1}} />
          <Image
            source={images.chevronRightIcon}
            style={{
              width: 20,
              height: 20,
              marginStart: 10,
            }}
          />
        </TouchableOpacity>
        <TouchableOpacity
          style={{
            flexDirection: 'row',
            paddingVertical: 10,
            alignItems: 'center',
          }}
          onPress={() => {
            navigate('Login');
          }}>
          <Image
            source={images.exportIcon}
            style={{
              width: 20,
              height: 20,
              marginStart: 10,
            }}
          />
          <Text
            style={{
              fontSize: fontSizes.h6,
              color: 'black',
              paddingStart: 10,
            }}>
            Đăng xuất
          </Text>
          <View style={{flex: 1}} />
          <Image
            source={images.chevronRightIcon}
            style={{
              width: 20,
              height: 20,
              marginStart: 10,
            }}
          />
        </TouchableOpacity>

        <View /** Tùy chỉnh */
          style={{
            height: 50,
            backgroundColor: colors.myWhite,
            justifyContent: 'center',
          }}>
          <Text
            style={{
              fontSize: fontSizes.h7,
              color: colors.noImportantText,
              paddingStart: 10,
            }}>
            Tùy chỉnh
          </Text>
        </View>
        <View
          style={{
            flexDirection: 'row',
            paddingVertical: 10,
            alignItems: 'center',
          }}>
          <Image
            source={images.globeIcon}
            style={{
              width: 20,
              height: 20,
              marginStart: 10,
            }}
          />
          <Text
            style={{
              fontSize: fontSizes.h6,
              color: 'black',
              paddingStart: 10,
            }}>
            Ngôn ngữ
          </Text>
          <View style={{flex: 1}} />
          <Text
            style={{
              fontSize: fontSizes.h6,
              color: 'black',
              paddingEnd: 10,
              opacity: 0.5,
            }}>
            Tiếng Việt
          </Text>
          <Image
            source={images.chevronRightIcon}
            style={{
              width: 20,
              height: 20,
              marginStart: 10,
            }}
          />
        </View>
        <View
          style={{
            flexDirection: 'row',
            paddingVertical: 10,
            alignItems: 'center',
          }}>
          <Image
            source={images.darkModeIcon}
            style={{
              width: 20,
              height: 20,
              marginStart: 10,
            }}
          />
          <Text
            style={{
              fontSize: fontSizes.h6,
              color: 'black',
              paddingStart: 10,
            }}>
            Chế độ nền tối
          </Text>
          <View style={{flex: 1}} />
          <Switch
            trackColor={{false: colors.switchOff, true: colors.switchOn}}
            thumbColor={isEnabledLockApp ? colors.switchOn : colors.switchOff}
            onValueChange={() => {
              setEnabledLockApp(!isEnabledLockApp);
            }}
            value={isEnabledLockApp}
            style={{marginEnd: 10}}
          />
        </View>

        <View /** Khác */
          style={{
            height: 50,
            backgroundColor: colors.myWhite,
            justifyContent: 'center',
          }}>
          <Text
            style={{
              fontSize: fontSizes.h7,
              color: colors.noImportantText,
              paddingStart: 10,
            }}>
            Khác
          </Text>
        </View>
        <View
          style={{
            flexDirection: 'row',
            paddingVertical: 10,
            alignItems: 'center',
          }}>
          <Image
            source={images.warningShieldIcon}
            style={{
              width: 20,
              height: 20,
              marginStart: 10,
            }}
          />
          <Text
            style={{
              fontSize: fontSizes.h6,
              color: 'black',
              paddingStart: 10,
            }}>
            Báo cáo sự cố kỹ thuật
          </Text>
          <View style={{flex: 1}} />
          <Image
            source={images.chevronRightIcon}
            style={{
              width: 20,
              height: 20,
              marginStart: 10,
            }}
          />
        </View>
        <View
          style={{
            flexDirection: 'row',
            paddingVertical: 10,
            alignItems: 'center',
          }}>
          <Image
            source={images.questionMarkIcon}
            style={{
              width: 20,
              height: 20,
              marginStart: 10,
            }}
          />
          <Text
            style={{
              fontSize: fontSizes.h6,
              color: 'black',
              paddingStart: 10,
            }}>
            Trợ giúp
          </Text>
          <View style={{flex: 1}} />
          <Image
            source={images.chevronRightIcon}
            style={{
              width: 20,
              height: 20,
              marginStart: 10,
            }}
          />
        </View>
        <View
          style={{
            flexDirection: 'row',
            paddingVertical: 10,
            alignItems: 'center',
          }}>
          <Image
            source={images.documentBlackIcon}
            style={{
              width: 20,
              height: 20,
              marginStart: 10,
            }}
          />
          <Text
            style={{
              fontSize: fontSizes.h6,
              color: 'black',
              paddingStart: 10,
            }}>
            Pháp lý & Chính sách
          </Text>
          <View style={{flex: 1}} />
          <Image
            source={images.chevronRightIcon}
            style={{
              width: 20,
              height: 20,
              marginStart: 10,
            }}
          />
        </View>
      </ScrollView>
    </SafeAreaView>
  );
}
export default Settings;
