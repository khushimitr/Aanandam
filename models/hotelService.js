const mongoose = require('mongoose');

const hotelServiceSchema = new mongoose.Schema({
    serviceName: {
        type: String
    },
    images: {
        type: [String]
    },
    price: {
        type: {
            premium: Number,
            regular: Number,
            _id: false
        }
    },
    accomodations: {
        type: [String]
    },
    details: {
        type: String
    },
    description: {
        type: String
    }
})

const Hotelservice = new mongoose.model('Hotelservice', hotelServiceSchema); 

module.exports = Hotelservice;