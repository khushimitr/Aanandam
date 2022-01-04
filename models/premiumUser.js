const mongoose = require('mongoose');
const User = require('./user');
const Room = require('./room');

const premiumUserSchema = new mongoose.Schema({
    user: {
        type: mongoose.Types.ObjectId,
        ref: User
    },
    room: {
        type: mongoose.Types.ObjectId,
        ref: Room
    },
    isRental: {
        type: Boolean,
        default: false
    },
    rentalFee: {
        type: Number,
        default: 0
    },
    dateInfo: {
        type: {
            checkIn: Date,
            checkOut: Date,
            _id: false
        }
    }
});

module.exports = new mongoose.model('Premiumuser', premiumUserSchema);

