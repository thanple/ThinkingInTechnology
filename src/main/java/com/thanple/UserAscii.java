package com.thanple;

/**
 * Created by Thanple on 2017/3/28.
 */

//L7LLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLLLvL7v7vvv7v77vvr7rr;rr77v77777v7LvLLYvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvYvLvLvYvv
//L7LvLvLvLvLLLvLvLvLvLvLvLvLvLLLvLvYvLLLvvvLvLvLvLvLLYvLvLvL77r7rrirr77vrrrJUkXPFuL7r77777r7r7vLvLvLLLLLLLvLvLvLvLvLvLvLvLvLvLLLvLvLvLvLvLvLvLvLLLvLLLv
//vvvLvLLLvLLLvLvLLLvLvLvYvLvLLYvYLLvLvLvLvLLLvLvLvLvLvLvv77r7LqZMq5kE8BMOEMB@B@B@@@BMMBM@MZk5vrr77vvLvLLLvLLLvYvLvLvLvLvYvLvLvLvLvLvLvLvLvLvLvLvLvLLYvv
//Y7YvYvLvLvLvLvLvLvLvLvYLLvLvLvLvLvLLLLLvLvLvLvLvLvLv7r7i7JFZ@B@@@B@B@B@B@B@BBMBMMO@BBMBB@B@BMFuLvrr7vvLvLLLvLvLvLvLvLvLLLvLvLLLvLvLvLvLvLvLvLvLvLLLvL7
//LvvLLLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvYLYvLvv777Y5NM@B@BBMBMM8OOO8OMMMG8MGMBMOOO8GOOMO@B@BBM8Uvrrr77vvLvYvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvv
//Y7LvLvLvLvLvLvLvLvLvLvLvLvLvLvYvLvLvLLLvLvLvLvLvvrvSMB@B@B@MBBBMBBBMMOMMBB@MBM8O@BBOMN8XZZGqEGMMMB@MGFJ7rr77LvYLLvLvLvLvLvLvLvLvLvLvLvLLLvLvLLLvLvLvL7
//v7LLvLvLvLvLvLvLvLvLvLvLvLvLvLvLLLvLLLLLvLvL7v77rXB@@@B@MBOMMMMBMBMMMBMBMM8BMM8E888MGMGqFXEZPEE8S0O@B@BBESv7rvvLvLvLvLvLvLvLvLvLvLvLvLvLvYvYvLvLvLvLvv
//Y7LvLvLvLLLLLLvvLvLvLvLvLvLvLLLvLvLLYvLvv77rr;rvZ@@B@MBMMMBMBMBMBMOOOqENOZZEGEZXEEXFq52NGNPqEXZZMG0FE8MB@B@Gjr77LvLvLvLvLvYvLvLvLvLvLvLvLvLvLvLvYvLLL7
//vvvLvLvLvLvLvLvLvLvLvYvLvLvLvLvLvLLLvv7v7J1SX0ZBBBMBMMMBMMOMMMOM8MMBMME0EPUFSFUkSZZZXXXNSSFkXSFXXO8NFFN8M@B@BNL77vvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLLv
//L7LvLvLvLvLvLvvvLvLvLLLvYvLLLvLvLvL77rriuM@M@B@MBMBMO8OMMOMGO8OMM8OEZ0kkNSN8EkSSSSFF55XPEqNPPNG550MZ11kk55G8O8M5vrvvLLLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvY7
//LvvLvLvLvLvLLLvLvLvLvLvLvLvLLLvLv7rrvU1EB@B@B@OOMBMBOMZEZMMMMBB@BM8ZPX2S5SSX5SXS2PNq0ZEOM@OGqOOZFXqZS5UNqP5NZ0EMZ1v77LvLvLvLvLvLvLvLvLvYvLvLvLvLvLvLvv
//L7YvLLLvLvLvLvLvLvLvLvLvLvLvLvL7771E@B@B@M@B@BBBBMBMBB8ZMBMMBMBOOOBMMO80E11U1UXkXq8GM0NqkFXUUUqOOPSuNOO0GZBMBOq10M8J77vvLLLvLvLvLvLvLvLvLvLvLvLvLvLLL7
//LvLLvLLLvvvLvLLLvLvLvYLLvLvLLL7rY8B@@BMMMBBBBBMBMBMBM@B@@@B@B@B@MOEXNO8EqP5qO@GE0Gq0kEO8GGNEMMNEOM01uFkZ0NG@B@B8jXO8U77LLYLLvLvLvLvLvLvLLLvLvLvLvLvLvv
//Y7LvLvLvLvLLLvLvYvLvLvLvYLLvv771BB@MMOMMBMBMBOBMBMBMMMBB@B@B@@@BBMMN8E8GMBBMOO@B@MOO8GMOMMBB@M@BMM@B@MOGOM@MBM@@MSP08kY7vLLvLvLvLvLvLvLvLvLvLvYvLvYvY7
//LvvLvLvLvLvLvLvvvLvLvLLLvYvLrv2B@@MMOBMBM@BBMBBBB@B@BBOGGM8ZXk1UJ77YYJJuuqZO0NSGNkF1j2qX0N2XGXJSBMGOMBO@MOMBB@B@B@OkqGPJ7LvLvLLLvLvLvLLLvLvLvLLLvLvLvv
//L7LvLvLLLvLLLLLvLvLvLvLvLvL7LN@@BBM8BBBMBMBMMMBB@B@@ME0kS11LL77r77v77r777rYJYuYLGNUFj7uqOGqFOZNu1O8qPP5uXGMMMMMMMOBqkSN1L7LvYvLLLvLvLvLvLvLLLvLvLvYvY7
//LvLLvLvLvLvLvYvLvLvLLLvLvL7vN@B@MBGBB@B@BBBBMBM@BMZqjLv77YLv77rrr777r7r777rL7YJ7vX2YjLr7jqPqOMENqGOOMBMOOOO8EMMMMkUZOGXkuv7LvLvLvLvLvLvLvLvLvLvLvLvLvv
//Y7LvLLLvLvYvLvLvLvLLLvLLLv7J@B@MBO0M@BBB@B@MBB@MOXF1Uv7riiirriiii:i:iii:i:iiiir7r7kN2uYi77jY11OOOMBqE8MMBB@MBMBMM8XqOBM05JvvLLLvLvLvLvLLLvLvLvLvLvLvL7
//LvvLvLvYLLvvvLvLvLvLvLLLvL7EB@MBMMOMMBM@B@OMOM8GONuJJUY7ri:i:ii::::::::::::::::iri;2qujJ777vJvvUFMB@NPk0M@MBBBMMOM8GGOB@G1vvvLvLvLvLvLvLvLvLLLvLvLvLvv
//L7LvYvLvLvLvLvLvLvLvLLLvL7L0B8OMMMMOBM@B@OZMMGXLUX2vvr7vL7iii::::,:::,,,,,:::,:::i:i7Lv7rrrivvv77r2O@MEPZM@MMM@BB8BGOOOG@OJ7LvLvYLLvLvLvLvLvLvLvLvLvL7
//v7LLvLvLvLvLvLvLvLvLLYLLvvvSZOOMM@MMMBM@M0ZM0ENUr7Lvrrirrrii::::::,,,:,::,,,,:,::::::::::::::i;77vrrL1255XM@MBMM@@BMOBMXPOLvvYvLvLvLvLLLLLvLvLvLvLvLvv
//Y7LLLvLvLvLvLvLvLvLvYvLvL7J0MMBMBO8MMMBM8NGGXUXSLr7v7;riii:::::,:...,.,.,,..,.,,,.,.,...,,::::::irvrrr7vuU0OMMMPSEBB@MMB21u7LLLvLvLvLvLvLvLvLvLvLvLvY7
//LvvLvLvYvLvLvLvLvYLLvYLL7LU8MMM@M0OB8MMGEGSGkuLuuvr7r;iiii::::::.,.,...,.......,.,...,.,.,,::::::iiirr;77LuZM@BO0SkqO@B@qPPvvLLLvLLLvLvLvLvLvLvLvLvLLv
//L7LvLvLvLLLLYvLvLvLvLvLvvLqOBMMMMNMM8OMZGS5XXu77LL7;iii:iii::::,,,,.,.,.....,.,.,.,.:,:,,,:,::::::iiir7rr7vYGM@MOqq0kq@BB5NvLvLvLLLLLvLvLvLLLvYvLvLLL7
//vvvLvLvLvLLLLLvLvLvYvYvL7uZBOMMMOO8MGMGZ0PXq5u7v77rri;:i::::::,:,,.,...........,.,.,.,,,,,,::::::::iirrr;77v5MMMMMN08EPM8FJvvLvLLLvLvLvLvLvLvLvLvYvLvv
//L7YvLvLvLvYvLvLvLLLvLvLvvjGMMOMOMGOMO8k2PZ88ZLv7rrrir;i:i:::::::,,,,,.,...........,,,,.,,,:,::::::::iirrrr77uZBMMO8XEMGSNPU7LvLvLvLvLvLvLvLvLvLvLLLvLv
//LvvLvLvLLLvLLLvLvLvLvLvL7YPBMMOO8ZGMON5qGMGX2J77r7rri;:i::::::::,,.................,.,,,,:,::::::::::iiri;rvLFO@MOOSEO@GNMuvvLvLvLvLvLLLvLvLvLvLvLvLvv
//L7YvLvLvLvLvLvLvLvLLLvLvvvqMBMMO8ZOMOkqM@OXjLv7r7iri;i:::::::,:,,.,.........,.......,.,,,,:,,,:,::::iiii;ir7LJqOBMMqP8@B0XL7LvLvLLLvLvLvLvLvLvLvLvLLL7
//LvvLvLvLvLvLvLvLLLvLLLvL7vSBBBMBGGGqG@ZOGE1uv7r7rri;i:::::,:,,.......,...,,..,.,.......,...,.,::iri::::iiri77Yj08MMGUqZME57LLLLLvLLLvLvLvLvLvLvLvLvLvv
//Y7LvLvLvLvLvLvLvLvLLLvYvLvF8BMMMO0N1ZNZ8GN2Lvrrriii:iir;r;r::,:,,.............,...........,:ii77uYL77i::iir;7LuFZEMOqkOO8JvvLvLLLvYvLvLvLvLLLvLLLLLvL7
//vvvLvLvLvLvYvLvLvLLLvLvLvvJ0MBOMOEqqZFkGZqJv77rriiivLLvvLuJYJ2uJ7r:. .       .   . . .,r7UkX15Jv7rrYJULriiirrLJXE8ZM0qMB0L7LvLvLvLLvvLvLvLvLvLLLvLvLLv
//Y7LvLvYvLvLvLvLvLvLvLvLLL7LSMMOMMNOXMPE8GSu77r7;;;Luuv7rrii;v7rr7vLr:,:,...  .....::::r7vYvrriii::iir7Jv7ii;rvjSMOOG8EMON77vLvLvLvLvLLLvLvLvLvLvLvLvL7
//L7vLvLvLvLLLvLvLvLvLvLvLvv7jZMMMMOGP8OqEG1jL7r;rivLLri:,.,.. ..,.::::::::,...,.,,,,:,:,,..     ..,..,:i7ii:iivjNOMOEEOGOXL7LvLvLvLvLvLvLvLvLvLvLvLvLvv
//L7LvLvLLLvLvLvLvLvLLLvLvLvv7FOMO8GGPqqGOM2Lv7i;;r:::::iiririii;ii:irr::.      ..  ..::r777YLL7v7vvvvjYuU2v:.r7F8MMOEZ8O027vvLvLvLvLvLvLvLvvvYvYvLvLvL7
//LvvLvLvLvLvLvLvLvLvLvLvLvYvv5MMMNEqMPNO@MEJJvr::,vqO8E1uvr;i::,:: .r7jSZEXvi   .iSJL77ri:.     ..,::i7vU1BBkiPO@B@MOEM8NL77LvLvLvLvLvLvLvLvLvLvLvLvLvv
//Y7LvLLLvLvLvLvLvLvLvLvLvYvL7uEBMENPZG088BB@B@GiiMBM7vi:..:ii;i:::..    .:J@B@B@B@B1::,..,:v7Y2u7vi::::i:,,@B u0SkP2U8G8SY7vvLvLvLvLvLvLvLvLvLvLvLvLvY7
//LvvLvLLLvLLLvLvLvLvLvLvLvLvvvNMMqNkZOk1@BB0@B@7 O@,:iriLSq7qB@0@G:7vi:.   1@B1Y8B@..,::7,YB@M@MijMXJrri:..B@ r8@7 :UEMNu7vvLvLvLvLvLvLvLvLvLvLLLvLvLvv
//L7LvLvLLLvLvLvLvLvLvLLLLLLLvvuGOME8qGSrO@Si75GU.MB:.;;;YF7.:OM@Br irv:... @B    vBL  ,r;:.u0NSr.irUuvi::..@BJ7uBL 7UqEj7v7vvvvvvLvLvLvLvLvLvLvLLLvLvL7
//L7vLvLvLvLvLvLvLvLvLvLvLLLvL77UBG5OEGN:MBur;:ir7U@U.:i:i:::,.,::.i:::, , 7B:   . ZB. :::::.. ..::::i:::: uBr:;7MU.iLjF7L7vvLvLvLvLvLvLvLvLvLvLvLvLLLvv
//L7LvLvLvLvLvLvLvLvLvLvLvLLLvL7L0P.PO8MrE@2777i:,.v@:..:,:::,,.,,:::,,., .BY ..... @@, ..,,,.,.,,,.,.,.  :BL.:r70O:iv7v7vLvLvvvLvLvLvLvLvLvLvLLLvLvLvL7
//LvvLvLvLvLvLLLvLvLvLvLLLvLLLvL7jY:r10ZGGBF7vr;ii:.uO.... .   . . . ..,:vM2 ,...,,,.EBkri::.. ....,,:ivSOBu.:i77qBvir.:rvvv7LvLvLvLvLvLvLvLvLvLvLvLvLvv
//Y7LvYvLvLvLvLvLvLvLvLvLvLvLvLvLvY7 .2uXM@1777ii:i:,r2F5vL77r7irr;rrr77LL:.,,:..,:::.ivuU1SN5kFS1Sk0Sk51L:.::;rv5@5.: .7rYvvvvvLvLvLvLLLvLvLvLvLvLvLLL7
//LvvLvLvLvLvLvLvLLLvLvLLLvLvLvLvLJv :,:,8BP7L7rii:::...,:ir777Lv7;;::::...:,:,..,::::....   . ... .     ..::ii7LSE5 :.:rrvLvvvvvLvLvLvLvLLLvLvYLLvLvLLv
//Y7LvLvLvLvLvLvLvYvLvLvLvLvLvLvLvY7,i;: 7@MLvvr;ii::::,,.  .     ..,.,,,,:::,:.,.:::::,:,:,:,,.,...,.:,::::iir7jFuL::,rr7L7LvLvLvLvLvLvLLYvLvLvLvLvLvY7
//v7LLvLvvLLvLvLvLvLvLvLvLvLvLvLvLvLi:rL::XBJL77iiii::,::,.,.,.,...,...:::ii:::,.:,::i:i:,.,.,.,,,.,,:,:,::ii;ivukrL:.:r:JvLvLvvvLvLvLvLvLvLvLvLvLvLvLvv
//L7LvLvLvLvLvLvLvLvLvLvLvLvLvLvv7Jv7i:vv:U02vLr;ii::::,:,,.,...... ..iiiir::,,...,,:irir:..........,,:,::::ii7LXv.:i:i,v1LvvvLvLvLvLvLvvvLvLvLvYvLvLvL7
//LvLLvLLLvLvLvLvYvLvLvLvLvLvLvLvvL1r:i;: :0uYv7ii:i::::,,...........:i:,:::,,.  .,,,::::;,........,,,,::::i:rvU5:.::::LFJ7vvLvLvLvLvLvLvLvLvLvLLLLLvLvv
//Y7LvLvLvLvYvYLvvLvLvLvLvLvLvvvv7vL57:::,:55LL7;iii:::,::,.,.......,i:...,,:::.,,:::,,.,ii ......,.,,::::i:irLUv ,:::jSJ7vvvvLvLvLvLvLvLLLvLvLvLvLvLLL7
//vvvLvYvLvLvLvYvLvLvLvLLLvLvLvLvL7vuSrii:.JSYv7r;:i:i::,:,,.,...,.,.;7ri;vvr7ri:irv5Fri:7i....,.,.,,::::i:iivLFvirLL1UY7v7vvLvLvLvLvYvLvLvLvLvLvLvLvLLv
//L7LvLvLvLvLvLvLLLvLvLvLvLvLvLvLvv7vYULJYSGPLL77;i:ii:::,:,:,,.,...,.;7;:::::7rrri::::i7i,.....,,:,:,::iiiirvYkMP1JJvv7v7LvLvLvLvLvLvLvLvLvLvLLLvLvLvL7
//vvLLvLvLvLvLvLvLLLvLvLvLvLvLvLvLvL7v7vvvL55uvv7rii:i::::,:,,,,...,,,,::::::,.::,,::i:i:,,:,,.,,:,:,::::iirrvJE277vvv7LvLvLvLvLvLvLvLvLvLvLvLvLvLvLvYvv
//Y7LvLvLvLvLvYvYvLvLvLvLvLvLvLvLvLvLvv7L777UuL77r;ii:i::::,:,:,,,:,:::::::::::::,:::::::,:,:,:,,:::::::ii;rr75Sv7v7v7LvLvLvLvLvLvLvLvYvLLLvLLLvLvLvLLL7
//LvvLvLvYLLvLvLvLvYvLvLvLvLvLvLvvvLvvvL7L77L2LLr7iiii::::::,:,:,:,:,:::,:,,.,,:,:.:::::::::::::,::::::iiri;ruFJ7v7LvLvLvLvLvLvLvLvLvLvLvLLLvYLLvLvLvLvv
//L7LvLvLvLvLvLvLvYvLLLvLvLvLvLvLvLvvvv7Lvv7vUuv7iiiiii::::::,:,:::,:,,..,,,,.,.,,:,:.,,:::::::,::::::iiriiiv217v7L7vvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvL7
//L7LLvLvLvLvLLLvLvLvLvLvLvLvLvvvLvL7vvL7L7v7uuY7rii:i:i::::::,:::::,:,::;i:::i::::::i::,::::::::::i:iiiiii7jkYv7vvvvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLLv
//L7LvLvYLLvLvLvLvLvLvYvLvLvLvLvLvLvvvv7L7v77vUjvr;ii:iii:::::::::i;YJuuJvJvLLuYjvLvJLYYuvr:::::::i:iiiiii7JS2v7v7LvLvLvLvLvLvLvLvLvLvLvYvLvLvvvLvLvLLY7
//vvLLvLLLvLvLvLvLvLvLvLvLvLvLvLvLvv7Lvv7L7v77Y1Lvrii::iii:::::::::ir7rri::::iriii:iir777v;7iii::i:ii;iii7Y52L7v7LvLvLvLvLvLvLvLvLvLLLvLvLvLvLvLvLLLvLvv
//Y7LLLvLvLvvvLvLvLvLvLvLvLvLvLvLvLvvvv7vvv7v7vuUL7;r:::i:i::::::::::::::::,.....,::::i:i:i:i:::::iirii;vY1FY7vvLvL7LvLvLvLvLvLvLvYvLLLvvvLvvvLLLvLvLvYv
//vvvLvLvLvLvLvLvLvLvLvLLLvLvLvvvLvL7L7vvvvLvv7LUj7vrrii:i:::::::::iii:i:i:i:::::iiiiiii:i:i:::::iir;rrvYk8Srr7v7LvLvLvLvLvLvLvLvLLLvLvLvLvvvLvLvLvLvLvv
//L7YLLvLvLvLvLvLvLvLvLvLLLvLvLvvvLvv7L7vvL7vrrrPOFLvrriiii:::i:::::::::iiiiir77riiiiii:::::::i:iir;rrv2PB@M27rr77vvLvLvLvLLLvLvLvLvLvLvLvLvLvLLLvLvLvLv
//LvvLvLvLvLvLvLvLvLvYvLvLvLvLvLvLvLvvvLvv7vrr7SB@0G1L7rir;i;r:i:::::::::::iiiii:::::i:::::::iiiirrrrYqZ2B15OOqSY7;r7LvLvLvYLLLLvLvvvYvLvLvLvLvLvLvYvLvv
//L7YvLvLvLvLLLvLvLvLvLvLvLvLvLvLvLvL7vvv77i7kMBM7qFEEFL7r7iiii:::::::::::::::::::::::::::::i:ii;rr7FOq7u8SO8O@@@OXL7r77LvLvLvLvLvLvLvLvLvLvLvLvLvLvLLL7
//vvLLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvLvv7rrUO@O@u YYruEMSL77ii:i:i::::::::,:,:,:,:::,:::,::i:::i;7uG0jrr2OX@BMMZMBB@G5v7rv7LvYvLvLvLvLvLLLvLvLvLvLLLvLLv
//L7LvLvLvLvLvLvLvYvLLLvLvLvLvLvLvLvLvv7rjM@B8@B.:JU7i;LEOqLvii:i:::::::,,,...,,,,,.,,:::::ii:iiuEEJri7rXBM5MBNEGEMM@BMqur77vvLLLvLvLvLvLvLvLvYvYvLvLvL7
//vvvLLLvLvLvLvLLLLLvLvLvLLLvLvLvLvvvvriJ@BOO@BF 77L7rii:vkOEUr::::i::::,:,,.,,,.,.,.,:::::::iLEZuiirrrrX@BG;Fj2OMNGGMB@MOjrrvvLLYvLvLvLvLvLvLvLLLvLvLvv
//L7LvLvLLLvLvLvLvLvLvLvLLLvLvLvL7vrrivP@BGOBB@i S0uL;rii::rSG81r::::::::,:,:,:::,:,,,:::::iJNOui:iir;7vjS@@GLquOBPGOGOOMBBqjr7vLLLLLvLLLvLvLLLvLvLvLvL7
//LvvLvLvLvLvLLLvLvLvLvLvLvLvLv7rrrvJXM@B8k@BMv.:F0kUriiii::,:uOMq7:::::::::::::,:::,::::ruZ82i:iiiiir7Lu;7MB2Pv5@OOq8OMGMB@MSr77LvYvLvLvLvLvLLLvLvYvLvv
//L7YvLvLvLLLvLvLvLvLLLvLvLvv7r7u58B@@@MOSGB@v::iLZu17iiiiiii:::vXOZSLr:::i::::,::::iiLuNGEJi:i:i:iirr7v15i7Xjv LB@MGNOOM8OB@BOj77vvLvYvLvLLLvLvLvLvLvL7
//vvvLLLvLvLLLvLvLvLvLvLvv77rLSBB@B@BMOZPOBMur7Li:O80Oi:iiiiii:i:i;J50NZPk22YYvJLu1PE8qXuviiiiiiiiiir7rvJEPNFvr,Y@BM0ZZMOMGMMM@BPLrvvLvLvLLYvYLLvLvLvLLv
//Y7LvLvLvLvLvLvYvLvLvL7vrr7XB@BB@@GOGO0kP@BurJL:iOBBB8,:iiii:::iirriir7YJUu5k0qPF1jYr::i;rii:iii:iiii77LuFSSLi:uBB8PNGE8OMZ8M@O@8urrr77v7LvLvLLLvLvLLY7
//vvvLvLLLvLvLLLLLvLvvrr71Z@B@MOMM8M8OOO8XMMrvUYiuj@MM@O:::i:i:i:i;rr7r;i:,,.,,::::::ii;r7ii:::::iiiiirv7YLjqMB8S@MOZ0EZZOZOEMGMMMM5Lv7v77rv7LvLvLvLvLvv
//L7LvLvLvLvLvLvLvvrr7uPB@@BMMO8@MBM8MMqqM@0uq1YLurB@OGMG:::::i:::iirr7;ri;:i:ii;iiiiirr7r::::::::i:ii7r7vvN@BMM@MOGOEZZ808MOZOOBM@B@MMOBZ57rr77vvLvLvY7
//LvvLLYLLvLvLvL77rLSMB@BGMMOM8MBBMMOBOEk@BOPGjFXu72k77vS5:::::::i:iiii;i;iriiiiiiiii;iri::::::::i:iirr7rrYM@BMMOOOMEOE8GZ8O8M8G8BMMB@BMB@BBX5L7r77LvLLv
//L7LvYLLvLvvrrr7jqM@B@OENZOMMZGBBBO@BOqEMBBPPuP8S: qMBOB@kr.,::i:i:iiiiiiiiiiiii:i:i:i:i:::::::::iirirriLBUYL2MMOMMGZ0G8O8Z8EO88OBOOOOOOMMBBB@OqYrrvvL7
//LvvLLLvL7v7JuPO@B@OBOGNESXZOqMNMOMZGqqXOOO5SPqZS:rE@OOZM01Z, ::::::i:iiiii:i:::::::i::::::,::::i:iir;ii@0EOEuEBOZEXZE8G8088M8OG8OOM8E@O0EMOBB@B@NLr77v
//L7LvL77rr78B@B@BMGZOOZEB@OOEOPEOG8OqZNEMMG0M@F012;7BBOM8@JMBv ,:::::::i::::::::::::::::,:,::::::iiiii:M@UMMOMSBNEkEEGGGZGG8EGOOqG8B8O80OBGM8ZM@B@MkYLr
//7rr7rvL5k8B@MBMMGG0MMMZMN8MNOMB@MGOMEqNBOOMMBEq517:OMNSkUv.qM1 .,:::::::::::,:::::::::::,:,:::iiiiii:2S12@MMG0MZXNZ8ZEGZEMGEGM0OG888MOZBMOBG5OOBB@B@Xr
//JvuuPZMMM88BMMOE8GMMMO8kOB@B@B@B@8OMEqMMMMB8@ML5ki:qOUOFXB@2i1J:,...:::::::::::,:::::,:,::::i:iiiii:7vU7ik8MZMOkGGOE80E0OOG8ON888EGGM8BM8M@OGPGMBMBMOq
//OOMBOOOOEZEMBMGMOOMM8Ok@MG8MBMGOZOOB8OGBqOBBBk78j7B501YiO@B@PPUv7Ji. .,:::::::::,:,:,,,:,::::iii::,iLLN5UvF0q@MN0MEO0ZGE8GGM88MMOMGGZMMO8BMEOMMBMBEZZ8
//OZMOOOM88ZGEOMM8OGMGMNOBO8GE@B@MBMMOO80BOXM5uEZEj87 LBU :7MB@B@MYLqkv..,:,:,:,:,:,:,:,:,::::i::::LF55P@8@BFrXBMPZZG0EG8EOZOMO8OEOZ8OMMMOMBG8@B@MOZO8MG
//OMOOOM8MGZNNEMONOOMMMNGO8ZXMOZG88BZ0MGP8MZukM@GP5N.. vBMqj:vPG0MB@7FXS,.,:,:,:,:,:,:,,,::::::r7rJq1GMk1iFG.jF@OOG8EE8BOMOZEOOM8MMM8G8OOMMBZ@BBMMZGGOOO
//O8OOMGONENOZE8MEGGMOMZqGOGGMOqGZGOEZBEEZBM8BMBB2XrFku:BB@@:kk27rSS7vNML .,,,,,:,,,,.,,,.,,::Y77v1XOB10EYEL28@MMM0qZZMGMMO8Z0MBBMOGE0OGOBBMOOGEGOM88ZOZ
//88OOE8Z8GO8OGZEOEG8MOZqMOOOMEOEBMGZOMO0GMBqMB@E17i7LjiS@M@;SBBMN1ii;70BO  ,,,:,:,,.,.. .:rr,,2Z8OBMOB@XUF1kOBGNMONE8GMOMMOGOOEqOGOO8ZMME00XESFGMOM8OGG
//O00q08M8OZE0EZO8M8OMMGOZZqEMOMOMOqBG8OZPOBqMMuFUivYrFu0B@@Pv@MMB@BMNSUuX7ru:,,:,,,,...2PXUYu0EPZOM@B@SJSZZJE@8ZZM8E8ME88GOB88E8NZMBOOB@GMqXMBEqFNGOGM8
//0XXGE8ZGE8EOZZZMOMOMOOXNOGG@B@BOqBGE0O000MGP2EOZv7vii:L@BBBX0MEOOBFqGEY:r7r:,:,,.,.,.uOPPuj@B@8qMBM@BNEMGGuOM8OMZONZO8OMGMO88OMM0NNOOOO@GqGOZZZ8OMEZNE
//NqGZZq0EZ088OEG8MZMMBOqMMNMMOPPNMGZMOO80ZO@riiii       :ri;Lvr::::i.::ii.   .::.,.,.,r:  .251Y.;i:;r7:i:.  OBGOGZZMN00MOMOGO88MG8kEOEqGGMMM8MOBMMOE0OX
//0EZEqNqNP0NGG8GOOM0MMMGMOZMM51GMOOGO0GGEqOB. ..:       :;ii:rrvr:i;:.r:,:i:.  ,,  .:r: .rk1uU:.7:i:ii::,.  MGM00PZ8MPZ00NMO80O8M08O8ZOM8EMMZkFUru0PZNP

public class UserAscii {
}
