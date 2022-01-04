const mongoose = require('mongoose');
const Service = require('./service');
const User = require('./user');

const employeeSchema = new mongoose.Schema({
    user: {
        type: mongoose.Types.ObjectId,
        ref: User
    },
    salary: {
        type: Number
    },
    isAvailable: {
        type: Boolean,
        default: true
    }    
});

module.exports = new mongoose.model('Employee', employeeSchema);