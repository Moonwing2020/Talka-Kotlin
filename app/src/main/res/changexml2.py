import os
import xml.etree.ElementTree as ET
from xml.etree.ElementTree import Element, SubElement, tostring
from xml.dom import minidom
import pandas as pd
import math

path_dicts = [
    'values',
    'values-en',
    'values-ar',  # values-ar 文件夹
    'values-in',  # values-id 文件夹

    # 其他文件
]

def prettify(elem):
    """Return a pretty-printed XML string for the Element."""
    def sanitize(element):
        # Iterate through all sub-elements
        for subelem in element:
            # If the sub-element contains NaN, convert it to a string or handle it
            if isinstance(subelem.text, float) and math.isnan(subelem.text):
                subelem.text = ''  # or handle NaN in some other way
            # Recursively sanitize sub-elements
            sanitize(subelem)
    
    # Sanitize the element to handle NaN values
    sanitize(elem)
    
    rough_string = ET.tostring(elem, 'utf-8')
    reparsed = minidom.parseString(rough_string)
    return reparsed.toprettyxml(indent="\t")

def xls_to_xmls(xls_file):
    print(os.getcwd())
    # 使用 pandas 读取 Excel 文件
    df = pd.read_excel(xls_file, engine='openpyxl')
    
    # Create dictionaries to hold elements for each output file
    xml_dicts = {}
    for i in range(len(path_dicts)):
        xml_dicts[i] = {'root': Element("resources"), 'filename': f"strings.xml"}
    
    # Iterate over rows in the DataFrame
    for index, row in df.iterrows():
        for i in range(0, len(xml_dicts)):
            string_element = SubElement(xml_dicts[i]['root'], "string", attrib={"name": row.iloc[0]})
            if i == 0:
                i = 2
            string_element.text = row.iloc[i + 1]
    
    # Write each dictionary's XML content to a file
    for i, xml_dict in xml_dicts.items():
        xml_string = prettify(xml_dict['root'])
        
        # 定义文件夹路径和完整文件路径
        folder_path = path_dicts[i]  # 文件夹名
        filename = xml_dict['filename']  # 从字典中获取文件名
        full_file_path = os.path.join(folder_path, filename)  # 组合成完整文件路径
        # 确保文件夹存在，如果不存在则创建它
        os.makedirs(folder_path, exist_ok=True)
        
        with open(full_file_path, 'w', encoding='utf-8') as f:
            f.write(xml_string)

# 使用函数
xls_to_xmls('ailiao2.xlsx')