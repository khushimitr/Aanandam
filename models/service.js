const mongoose = require('mongoose');
const User = require('./user');
const Employee = require('./employee');
const Hotelservice = require('./hotelService');

const serviceSchema = new mongoose.Schema({
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User' 
    },
    employee: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Employee' 
    },
    hotelService: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Hotelservice'
    },
    address: {
        type: {
            pickUp: String,
            destination: String,
            _id: false
        }
    },
    description: {
        type: String
    },
    dateInfo: {
        type: {
            servingDateStart: Date,
            servingDateEnd: Date,
            _id: false
        }
    }
})

module.exports = new mongoose.model('Service', serviceSchema);