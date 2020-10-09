# sender 
ffmpeg -i http://living.muzhifm.com/muzhifm/kmlive2.m3u8\?auth_key\=1632634402-0-0-5d0fcb0b5a596dc01f1544c33eb376ac -f wav - 2>/dev/null| ./roc-send -vv -s rtp+rs8m:224.0.0.143:10001 -r rs8m:224.0.0.143:10002 -d wav

# recv
./roc-send -vv -s rtp+rs8m:192.168.20.173:10001 -r rs8m:192.168.20.173:10002 -d wav -i ~/ys1-yuan.wav
./roc-send -vv -s rtp+rs8m:224.0.2.8:10001 -r rs8m:224.0.2.8:10002 -d wav -i ~/ys1-yuan.wav


./roc-send -vv  -s rtp+rs8m://192.168.0.3:10001 -r rs8m://192.168.0.3:10002 -i file:- --input-format wav < ~/ys1-yuan.wav

./roc-send -vv -s rtp://192.168.0.3:10001 -i file:- --input-format wav <./input.wav
./roc-send -vv -s rtp://192.168.0.3:10001 -i file:- --input-format wav <./input.wav


./roc-send -vv  -s rtp+rs8m://231.0.0.11:10001 -r rs8m://231.0.0.11:10002 -i file:- --input-format wav < ~/1.wav
./roc-send -vv  -s rtp+rs8m://192.168.20.143:10001 -r rs8m://192.168.20.143:10002 -i file:- --input-format wav < ~/1.wav


 ffmpeg -i http://living.muzhifm.com/muzhifm/kmlive2.m3u8\?auth_key\=1632634402-0-0-5d0fcb0b5a596dc01f1544c33eb376ac -f wav - 2>/dev/null| ./roc-send -vv -s rtp+rs8m://192.168.20.143 -r rs8m://192.168.20.143  -i file:- --input-format wav
