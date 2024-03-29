import {defineStore} from "pinia";
import axios  from "axios";
import {ApiUrl} from "@/helper/ApiHelper";


export const useUserStoreUpdate = defineStore('user', {
        state: () => ({
            newUser:'',
            users: []
        }),
        actions: {

            //in AdminHomeView, AdminUserView, AdminCourseUserView --- ADMIN
            async showUsers() {
                try {
                    const userResponse = await axios.get(ApiUrl('/admin/users'), {
                        headers: {
                            'Authorization': 'Bearer ' + window.localStorage.getItem('accessToken')
                        }
                    });
                    
                    
                    this.users = userResponse.data
                    
                    const userIds = userResponse.data.map(user => user.userId);
                    
                    return userResponse.data;
                } catch (error) {
                    console.error('Error loading users:', error);
                }
            },  async creatUser(newUser){
               

                const userResponse = await axios.post('http://localhost:8082/admin/users', newUser )
                this.users.push(userResponse.data)

            },

            // in ProfileView, AdminHomeView --- APPUSER, ADMIN
            async getUserData(userId) {
                const response = await axios.get(ApiUrl(`/users/${userId}`), {
                    headers: {
                        'Authorization': 'Bearer ' + window.localStorage.getItem('accessToken')
                    }
                });
                return response.data;
            },

            // in ProfileView, AdminHomeView --- APPUSER, ADMIN
            async updateUser(userId, updatedUserDto) {
                if ('userId' in updatedUserDto) {
                    delete updatedUserDto.userId;
                }
                await axios.put(ApiUrl(`/users/${userId}`), updatedUserDto, {
                    headers: {
                        'Authorization': 'Bearer ' + window.localStorage.getItem('accessToken')
                    }
                });
                const user = this.users.find(user => user.userId === userId);
                if (user >= 0) {
                    this.users.splice(user, 1, {
                        userId: userId,
                        ...updatedUserDto
                    })
                }
            },

            //in AdminUserView, ProfileView(user) --- ADMIN, APPUSER
            async deleteUser(userId) {
               
                await axios.delete(ApiUrl(`/users/${userId}`), {
                    headers: {
                        'Authorization': 'Bearer ' + window.localStorage.getItem('accessToken')
                    }
                });
                window.localStorage.clear()
            }
        },
    }
);
