## Smart Pecifier application :
This application is a prototype of a smart pecifier capable of mesuring baby temperature, displaying it in a mini 7 Segment display, store data in ThingSpeak cloud, and view the data in an Android application.

The IoT prototype is realised using a ESP8266 NodeMCU.
To run the code please install arduino [version 1.8.5](https://www.arduino.cc/download_handler.php?f=/arduino-1.8.5-windows.zip) for windows or here for [linux](https://www.arduino.cc/download_handler.php?f=/arduino-1.8.5-linux64.tar.xz) . Dont forget to install the missing libraries.

![enter image description here](https://lh3.googleusercontent.com/tpEJsAGoAB_JfYvY9fEd4yItTkGjQjK2nezvShLp29U4-hwZqU4zKlX_Yg5g2dFF8LJzoivzD53NaqGIt2mEWrr3H3jBUUNUWPbSQuOPWZqoTyzb7DrF_17147QCZfjhyRYR9bAUnl4h1GqZ5Bmbi5ywb5xJmQthwfiN2SRnBvRsFBBBa7eBcw8TXpZae5Zk_xo0PW3P3m62slFiQuvPRDOH6Sb095NVS4qQHsmdajA8rqDDXixAcL1LZMWsOtl-UL5TxbhrE-1Re5dzLQyMyXVoMoFswdDcHMiufBliBuH792KSewpLNBSt3f21pqiLvY8OEDnDVx7upd9g0ivBlHupE7pKscliMEwWhyCa1Majbvd97VmSn3fOFKfJ6QPycSqMVf4Nj0OdMIRMDmS5ZgK3GiC9Fhj3Fat09EN884FrH5Qlx9leEwvH5xITzwBf1QIWiV211KP3BwjLANVc4FEfjoO_luyYKP32fp5NUmFp7YiyEO6rY6e3Z7GPVC-PLXlZN4wNSCaAD2EkTavB5oHRZpCYWl3QshyCQZiUqyysllcZHJruQyssy2m1moabMxLfdxB74ZqUO7gK_W5jENOIUITSu9McnqkdxjD3xXcrErPFlKIivL9zVQ2sIxVm0tLarFRPzoFFS9czxrLEB8BjgOdy6y7jp9CJvG8X6cUzuT7g_LHoMn6OiBdTVrSQcXrIZ_KJsL2aVnwcs4w9ajiVw1--oVqOcDH4-x7qIvkB0_me=w270-h480-no)


![enter image description here](https://lh3.googleusercontent.com/xGqvbaGEyjBEo-l5-U1TTDbqq1Yvkr_GOlVilfG4icBHmWiXlm2iS_ItKbQC2jjo-zK3RA4lTsHxiUuOEjC_3A_uuldOHf0fz8NKvJnjYbt9wsN3DrXD9mEJgSe4uR5z6heZ-4_uw7bm-ReyEu1aRbjv5uoq6_COzGsCnTzsf5oiuMUK69OWjO0yQGg9Jj0tDrHo6BMMxA5GzSGc9YnkarbaQck--awYyTwriSsDrTa8jM-Uw7JkW1zq7iCJrTPzS9GeVYhsrzjJ76nCTrZsEdcEBqXTyYIHJs3yyLs82WRV7OOKTLUQiJz4vY7DJ6G4RioBj42nYpG_aEwKQk7fkDkeSuJASMfcDDbCMjjANY9H29Ipbgvra2GmuHlVmzhgqzr_UalGEGg6691S0qnoHJyc1nMSyJ5NQ7BRmXstPVs9jZ1tWTLmBUwDQTo8agNG_in2z_UNyNOa9pZLbXcJc0FpSNw9LzvH29Wuhvk3BNWJXghECj2_buUzYUI8iyNt_qwS6cvgBizspEUhXDxahAN6JMuWds03hsyi0bpIFgBpXyCWZ7E0zPdygrHzIF_gQoVYTmF8m5twvzRspSKND52SqK6EHhjYWBEPC9u6QjjLW6HmnehxXv2YdyQAHb7Hj38XQ0odegfIyWp_1N1wHCXg-xLPQlyLGSkT0rb9qhE7YGBZI46PnhbK2YES1LXFC-3rjBs7oTiZPMrBbSK5TzhUU_0dTq5iOF74Cwsp-Ak3EZgI=w270-h480-no)



![enter image description here](https://lh3.googleusercontent.com/_jdnBnC6q7eqS3W5E-Q8o2hfB2U6KXh27Ur6avdie48-EpHfk_-BSo84W13OWXyLfVo9wAvkm40gtqieAqP3r55lVGBGpQAADBWd2WYnJCuxc7jtujpHuVpNKaRnynw4LoDwrSOyk1ON1MIymCZZq36MsjG3PRwjo9wpMYcZm7W8giVfwpECJwFVSNGb2L3MC68a3EAB4YxgyhjuR995_5dmdm0tOlfP5gBmHvAsHMEhFP2b-MAYY1DppR6thc1NPIIl90ADKyf_lh2ss7YRma47FPkA6YgzEGoB9C8OYLqy567rEuCE_Zt6j7QjVeA_6j_KkYNwcZ1sv9R6QqXGpNS9JoCphRH1piprO4qkyrJ_AMKxNFLXtyV5-g5zbANNsALQPPOXIfy9Tti-UmqBNfgGZ2P7Bqz_NmIdhcqHbp2gkro75SXMe4yi5ItBssmoLvPim0Bppd6FfEOBsSJC0fqs7zSAqXwbwdnUIARKLw6pILstUoygEASUf6izg89B1ll2gysyrKgr5no51K8T2gV6yPBrUyQZJCqilcsffBRFdr9vLTnIDBhHvEJLncYInW8EE72nZMQvdUggejecJ1ceByMUiSaZQySFJNezlyjDDQ-wIMZEmWgA2OQzL762fTiwVBgpV2XNkH2YEau7qcI1qvLJvh7cbfsc83XiN1Yqo1lFBhtGtupfR4fEMquiqVaiSLd1fOyf-LevPgV8BcbwX30AxGIrCJUuwv0xrtemjtoQ=w270-h480-no)

![enter image description here](https://lh3.googleusercontent.com/XHmaxq_v4ZLj7wMoogXgkalsj_UDTNmBhqEfbNQaSANwZEmhWFiyEmdISH8H6pIj62NhGCkkfhIHuARaNpTuKG4SKQWrSrU1tNABCPeNqVKY2moERklQYhpmtuOXQvvvX6KOmA89rSwig1ZqGxm1WHgrmXVFd0alIICKODVs5okk7iONV4KKE9xp0kzI8_XzbrmK4npt7Z2CU7VnPtxKL7WweqLXUOYewZlPdKiKwKGzhrKzA99tfbJtI9WfHDpZgYUeglY10pZcLjaG8cPFQw2E3SC4utdXnhaXeEw4mv96NQ3amwU8ocxqgi8qn7XfejYoalvkPtXc5vRT6QGTogdNkP1L7qoNRObhEsU8X_CrMgOItOKRQIQuAwqCZ-C2iRgMhyo_USjNqtA1SiYrMu23yCsiEvxopnL8Wc_nYlproSnVNYRbZ2mgn540AjpCisbpQCv4qg41KRyTPtwhTEobDvXJz_rIfMCwAgb_zF3LBN6JtCxdDndKOtuWQyKiWxwQ7P3pvFpXkIrS0OrtW4H3GtUOHNSyK4n6-_DHfrc7PUb5h3t2ufKP5Ud3I7RBJNkKdQ440gl7FBLcmW9zQWgD0V2anLcXQfWcVE7L9_pFML70dByBBCzZIk-U04LX-jZlck-fLY4S6EfnKvnHcLBMlhSBsZOKctKYsUVaJPjBd-UqCAQftOUaM9p0Rj5cxUtGe54MC0IYTOKsgkSuSaCI-_CjsSFc1rIWt79CIILKFAut=w270-h480-no)

![enter image description here](https://lh3.googleusercontent.com/HwAM5C2ImUiEB9vOJ_HrYa66rNgE5rgiTf2Lroa8UD3kxCcNKBcTfxZ5acQKgTc4YpPbokZmelalZEPBmorkCUcNMSovRIV5_QXd0XXRFXUD28S5U_4JrIQGbLJgt_PLkMtaSsMGFl4V1SL5rJAIWCg863OdFw99a1XbOEAtstCYPhkutQHPmDp_BX8Itl8NVXxUhkwKiBNg9PqhQnGqRwwBW8pRNpliM4NI0UV0HIIT_6tOyeuVTd6B8SXEkUYHfVLnOP3NhouiID8j7o7GyP7u7H__eRYCJLZLmllyQYqXChLdtSEhWeywXdflNfn8VSDbWJPoOrjz-rzFfGF1Kvn6ZL9wlQ3NneH12vR_Vs-_vJThsMGyjZ2Jg7nWdg7lLsuHk38dNiiUlNcMEfUyE8Oa1EYpj-pN5Frg4Cexzss8nS_zZvqqeGb1eFmiW-IJQn8X80xo_MvdU5c6krYQPoAkh_rbyzqHc3l0rlvYG3b1Y3eW5UCddwYotqbNwQgtcCn8Oxv3_I5-fm38S79ajuzvx4QYOES1k-5XY9-heClEnAA2dSfxVcCdESHpemJm1Mm4K1BRF2lFBXZFVn9XIvah4G9hYa3967Lnnpwf9YM0Tbej1ZK4WjHn7FDEFEn-t7ZgBucswr2SdgQvZLJQlr-V5mXWSOBZ-SlBu6KCLmFPkuWPcrwqHtlj5Xs7uZ6chzp8ApWEMkMIDJijUsRhdQsfLFbVRGYuwl8hPVij9E_NCruo=w270-h480-no)

![enter image description here](https://lh3.googleusercontent.com/eRpojRBLW8Ut2-RrwRL0UpssN6WBAS_Wb_StXy1VSOV1Ya5UG3ihtdpJoJupaNOHHj6K-iNFcORRjHpZxTBN54jRzVyMhetRaU82EP4owLa21OdQ196LKV5UyEXcj0WIa9Pwzcyn5NXkShZ31oYpRlGwLNrw1z2vCf8cuEPjbJYgbPMo_NvJUmGjyubM7Myo8IauKnMBsZfKhqICrY5DwZYx9xD-sbaN8kumLO7LMgpIK3X5iG4exTRkeVTarHLlKgE68MWEM3GWfB9Jaxa8nK6kAkariU-ScjndqCShRmy5hhA1FZtR844E1OBRiy6mBWtEyYyukEzMWAj-00hUffPLAGEWWTFawjlTe24Nb5Hu1bCxVSUlpRi15qK9s2ROfuvaw8DjzS49wVfkgb1OPOdfH7EWI6lrZRAtgFzrOQ5ErA9YAQdUu8FGxy2bIRiRIvzntgTQILdauelMfcgToBsASOlIJvpn0D9innx6bbbIQ_pai3tshnOhr0UwTHG7iR-AMU_qCgh-i874uPsFYxN0U3ejE9Z2YAMdsR9HiiRqZKw5CWtvMq4ZKEc0pWXXC6m29t5YvKLds8w6U_O-kstzAcKTSLsT2TGm1fr8jST3u_catWW_GrdVkHDLDWmeAZwHf6ABOH_p5hwgRrlvVEGx__dDGo2JypkOQYHufvAJ79_1eAefTCMjQSATKbrhGcheqQPrR8oNsIS6x5st9-BehiKmIkf9G7rheIPadpqLZOzN=w270-h480-no)
