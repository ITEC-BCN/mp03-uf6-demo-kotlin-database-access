package model

class Laptop {
    private var index: Int
    private var brand: String
    private var model: String
    private var price: Float
    private var rating: Int
    private var processorBrand: String
    private var processorTier: String
    private var numCores: Int
    private var numThreads: Int
    private var ramMemory: Int
    private var primaryStorageType: String
    private var primaryStorageCapacity: Int
    private var secondaryStorageType: String
    private var secondaryStorageCapacity: Int
    private var gpuBrand: String
    private var gpuType: String
    private var isTouchScreen: Boolean
    private var displaySize: Float
    private var resolutionWidth: Int
    private var resolutionHeight: Int
    private var os: String
    private var yearOfWarranty: String

    constructor(
        index: Int,
        brand: String,
        model: String,
        price: Float,
        rating: Int,
        processorBrand: String,
        processorTier: String,
        numCores: Int,
        numThreads: Int,
        ramMemory: Int,
        primaryStorageType: String,
        primaryStorageCapacity: Int,
        secondaryStorageType: String,
        secondaryStorageCapacity: Int,
        gpuBrand: String,
        gpuType: String,
        isTouchScreen: Boolean,
        displaySize: Float,
        resolutionWidth: Int,
        resolutionHeight: Int,
        os: String,
        yearOfWarranty: String
    ) {
        this.index = index
        this.brand = brand
        this.model = model
        this.price = price
        this.rating = rating
        this.processorBrand = processorBrand
        this.processorTier = processorTier
        this.numCores = numCores
        this.numThreads = numThreads
        this.ramMemory = ramMemory
        this.primaryStorageType = primaryStorageType
        this.primaryStorageCapacity = primaryStorageCapacity
        this.secondaryStorageType = secondaryStorageType
        this.secondaryStorageCapacity = secondaryStorageCapacity
        this.gpuBrand = gpuBrand
        this.gpuType = gpuType
        this.isTouchScreen = isTouchScreen
        this.displaySize = displaySize
        this.resolutionWidth = resolutionWidth
        this.resolutionHeight = resolutionHeight
        this.os = os
        this.yearOfWarranty = yearOfWarranty
    }

    override fun toString(): String {
        return "Laptop(index=$index, brand='$brand', model='$model', price=$price, rating=$rating, processorBrand='$processorBrand', processorTier='$processorTier', numCores=$numCores, numThreads=$numThreads, ramMemory=$ramMemory, primaryStorageType='$primaryStorageType', primaryStorageCapacity=$primaryStorageCapacity, secondaryStorageType='$secondaryStorageType', secondaryStorageCapacity=$secondaryStorageCapacity, gpuBrand='$gpuBrand', gpuType='$gpuType', isTouchScreen=$isTouchScreen, displaySize=$displaySize, resolutionWidth=$resolutionWidth, resolutionHeight=$resolutionHeight, os='$os', yearOfWarranty='$yearOfWarranty')"
    }
}