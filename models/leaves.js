const mongoose = require('mongoose');
const Employee = require('./employee');

const leaveSchema = new mongoose.Schema({
    employee: {
        type: mongoose.Types.ObjectId,
        ref: Employee
    },
    dateInfo: {
        type: {
            from: Date,
            to: Date,
            _id: false
        }
    },
    title: {
        type: String
    },
    description: {
        type: String
    }
})

module.exports = mongoose.model('Leave', leaveSchema);