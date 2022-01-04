const mongoose = require('mongoose');

const roomSchema = new mongoose.Schema({
    roomId: {
        type: Number
    },
    roomName: {
        type: String, 
        required: true
    },
    details: {
        type: String
    },
    roomtype: {
        type: String,
        enum: ['1BHK', '2BHK']
    },
    location: {
        type: String,
        required: true
    },
    isVacant: {
        type: Boolean,
        default: true,
        required: true
    },
    accomodations: {
        type: [String]
    },
    images: {
        type: [String]
    },
    maxPerson:{
        type: Number,
        enum: [1, 2]
    },
    cost: {
        type: [Number],
        default: [6000, 70000],
    }
})

module.exports = new mongoose.model('Room', roomSchema);