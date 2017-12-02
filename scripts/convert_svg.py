import bpy
import math

def delete_all():
    for item in bpy.context.scene.objects:
        bpy.context.scene.objects.unlink(item)
    for item in bpy.data.objects:
        bpy.data.objects.remove(item)
    for item in bpy.data.meshes:
        bpy.data.meshes.remove(item)
    for item in bpy.data.materials:
        bpy.data.materials.remove(item)

delete_all()

def convert_svg(filepath, outpath):
    delete_all()
    bpy.ops.import_curve.svg(filepath = filepath)
    curve = bpy.data.objects["Curve"]
    bpy.context.scene.objects.active = curve
    bpy.ops.object.modifier_add(type = "SOLIDIFY")
    bpy.context.object.modifiers["Solidify"].thickness = 0.05
    curve.select = True
    bpy.ops.object.convert(target = "MESH")
    curve.rotation_euler[0] = math.radians(-90)
    curve.rotation_euler[1] = math.radians(-90)
    bpy.ops.object.transform_apply(rotation = True)
    bpy.ops.export_scene.fbx(filepath = outpath)

for i in range(146):
    filepath = "/Users/hokada/develop/scala/martha/example/element_%05d.svg" % i
    outpath = "/Users/hokada/develop/scala/martha/example/element_%05d.fbx" % i
    convert_svg(filepath=filepath, outpath=outpath)

# bpy.ops.import_curve.svg(filepath = "/Users/hokada/Desktop/trebleclef_big.svg")
bpy.ops.import_curve.svg(filepath = "/Users/hokada/Desktop/trebleclef_only.svg")

curve = bpy.data.objects["Curve"]

bpy.context.scene.objects.active = curve
bpy.ops.object.modifier_add(type = "SOLIDIFY")
bpy.context.object.modifiers["Solidify"].thickness = 0.05

curve.select = True
bpy.ops.object.convert(target = "MESH")

curve.rotation_euler[0] = math.radians(-90)
curve.rotation_euler[1] = math.radians(-90)
bpy.ops.object.transform_apply(rotation = True)

bpy.ops.export_scene.fbx(filepath = "/Users/hokada/Desktop/trebleclef_green_scale.fbx")
