const mongoose = require('mongoose');
const User = require('../models/user'); 
const bcrypt = require('bcrypt');

const seedEmployeeUsers = [
    {
        username:'Surjit Singh',
        password: '12345',
        email: 'Surjit@gmail.com',
        isEmployee: true,
        dateJoined: '2021-04-15'
    },
    {
        username:'Jagjit Singh',
        password: '12345',
        email: 'Jagjit@gmail.com',
        isEmployee: true,
        dateJoined: '2019-02-20'
    },
    {
        username:'Charanjit Singh',
        password: '12345',
        email: 'Charanjit@gmail.com',
        isEmployee: true,
        dateJoined: '2019-04-25'
    },
    {
        username:'Ranjit Singh',
        password: '12345',
        email: 'Ranjit@gmail.com',
        isEmployee: true,
        dateJoined: '2020-09-17'
    }
]

const hashPassword = async (seedEmployeeUser) => {
    const hashedPassword = await bcrypt.hash(seedEmployeeUser.password, 12);
    return hashedPassword; 
}

//hashing the passwords of the users
for(let seedEmployeeUser of seedEmployeeUsers)
{
    hashPassword(seedEmployeeUser).then((res) => {
        seedEmployeeUser.password = res;
    })
}

const addEmployeeUsers = async() => 
{
    const result = await User.insertMany(seedEmployeeUsers);
    console.log(result);
}

module.exports = addEmployeeUsers;