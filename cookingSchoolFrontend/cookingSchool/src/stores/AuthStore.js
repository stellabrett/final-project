import {defineStore} from 'pinia';
import axios from 'axios';
import {ApiUrl} from '@/helper/ApiHelper';
import jwtDecode from 'jwt-decode';

export const useAuthStore = defineStore('authentication', {
    state: () => ({
        user: null,
        accessToken: null
    }),
    actions: {
        getToken() {
            return this.accessToken;
        },
        getRole(){
            const decodedToken = jwtDecode(this.accessToken);
            return decodedToken.role;

        },
        getUserId() {
            const decodedToken = jwtDecode(this.accessToken);
            return decodedToken.userId;
        },
        async login({username, password}) {
            const response = await axios.post(ApiUrl('authenticate'),{username:username, password:password})
            if(response.status === 403){
                throw new Error('User nicht gefunden')
            }
            const token = response.data
            if(token === '') {
                throw new Error('Token nicht gefunden')
            }
            this.token = token
            
            window.localStorage.setItem('accessToken', token)
            function parseJwt (token) {
               
               return "";
            }
            parseJwt(token);
        },
       
        logout() {
            this.user = null
            window.localStorage.clear()
        }
    }
})