//
//  StatusBar.swift
//  iosApp
//
//  Created by Harshal Chaudhari on 03/09/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import UIKit


@objc public class StatusBar: NSObject {
    
    @objc public static func setStatusBarColor(red: CGFloat, green: CGFloat, blue: CGFloat, alpha: CGFloat) {
        
        let color = UIColor(red: red, green: green, blue: blue, alpha: alpha)
        
        // Get the active window scene
        guard let windowScene = UIApplication.shared.connectedScenes.first as? UIWindowScene,
              let window = windowScene.windows.first else {
            return
        }

        let topPadding = window.safeAreaInsets.top
        
        let statusBarView = UIView(frame: CGRect(x: 0, y: 0, width: UIScreen.main.bounds.width, height: topPadding))
        statusBarView.backgroundColor = color
        
        // Ensure the statusBarView is added to the window
        window.addSubview(statusBarView)
    }
    
}
