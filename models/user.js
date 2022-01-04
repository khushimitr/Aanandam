const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
    username: {
        type: String,
        required: true,
    },
    password: {
        type: String,
        required: true
    },
    email: {
        type: String,
        required: true
    },
    image: {
        type: String,
        default: 'kutta'
    },
    dateJoined: {
        type: Date
    },
    contact: {
        type: Number,
        default: 9999999999
    },
    address: {
        type: String,
        default: 'Saharanpur'
    },
    isEmployee: {
        type: Boolean
    },
    isPremium: {
        type: Boolean,
        default: false
    },
    availedServices: {
        type: Number,
        default: 0
    }
})

module.exports = new mongoose.model('User', userSchema);