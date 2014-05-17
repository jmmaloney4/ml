//
//  MLARFFAttribute.h
//  ml
//
//  Created by Jack Maloney on 5/17/14.
//
//

#import <Foundation/Foundation.h>

static NSString* STRING_ATTRIBUTE_DATATYPE_SPECIFIER = @"STRING";
static NSString* NUMERIC_ATTRIBUTE_DATATYPE_SPECIFIER = @"NUMERIC";
static NSString* NOMINAL_ATTRIBUTE_DATATYPE_SPECIFIER = @"{";

#define STRING_ATTRIBUTE 0
#define NUMERIC_ATTRIBUTE 1
#define NOMINAL_ATTRIBUTE 2

// Date Not Supported yet
// static NSString* DATE_ATTRIBUTE_DATATYPE_SPECIFIER = @"|";

@interface MLARFFAttribute : NSObject

@property (nonatomic, readonly, strong) NSString* name;

@end
